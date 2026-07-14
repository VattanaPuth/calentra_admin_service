package com.tech.sv.calentra.admin_service.services.impl;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tech.sv.calentra.admin_service.entities.FileMetadata;
import com.tech.sv.calentra.admin_service.enums.FileCategory;
import com.tech.sv.calentra.admin_service.properties.FileUploadProperties;
import com.tech.sv.calentra.admin_service.repositories.FileMetadataRepository;
import com.tech.sv.calentra.admin_service.services.MinioService;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService{

    private final MinioClient minioClient;
    private final FileMetadataRepository fileMetadataRepository;
    private final FileUploadProperties uploadProperties;

    public FileMetadata uploadFile(MultipartFile file) {
        validateFile(file);

        String extension = getExtension(file.getOriginalFilename());
        FileCategory category = FileCategory.fromExtension(extension);
        String bucketName = category.getBucketName();
        String storedFileName = UUID.randomUUID() + "." + extension.replace(".", "");

        try {
            ensureBucketExists(bucketName);

            try (InputStream inputStream = file.getInputStream()) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucketName)
                                .object(storedFileName)
                                .stream(inputStream, file.getSize(), -1L)
                                .contentType(file.getContentType())
                                .build()
                );
            }

            FileMetadata metadata = FileMetadata.builder()
                    .originalFileName(file.getOriginalFilename())
                    .storedFileName(storedFileName)
                    .bucketName(bucketName)
                    .category(category)
                    .contentType(file.getContentType())
                    .size(file.getSize())
                    .build();

            return fileMetadataRepository.save(metadata);

        } catch (Exception e) {
            throw new RuntimeException("File upload failed: " + e.getMessage(), e);
        }
    }

    public void deleteFile(UUID fileId) {
        FileMetadata metadata = fileMetadataRepository.findById(fileId)
                .orElseThrow(() -> new IllegalArgumentException("File not found: " + fileId));

        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(metadata.getBucketName())
                            .object(metadata.getStoredFileName())
                            .build()
            );
            fileMetadataRepository.delete(metadata);
        } catch (Exception e) {
            throw new RuntimeException("File deletion failed: " + e.getMessage(), e);
        }
    }

    
    /*
     * |-----------------
     * |VALIDATIONS BLOCK
     * |-----------------
    */
	private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String extension = getExtension(file.getOriginalFilename()).toLowerCase().replace(".", "");
        List<String> allowedExtensions = uploadProperties.allowedExtensions();

        if (allowedExtensions == null || allowedExtensions.isEmpty()) {
            throw new IllegalStateException("Allowed file extensions are not configured");
        }

        if (!allowedExtensions.contains(extension)) {
            throw new IllegalArgumentException("File type ." + extension + " is not allowed. Allowed: " + allowedExtensions);
        }

        long maxBytes = uploadProperties.maxSizeMb() * 1024 * 1024;
        if (file.getSize() > maxBytes) {
            throw new IllegalArgumentException("File exceeds max size of " + uploadProperties.maxSizeMb() + "MB");
        }
    }

    private void ensureBucketExists(String bucketName) throws Exception {
        boolean exists = minioClient.bucketExists(
                BucketExistsArgs.builder()
                				.bucket(bucketName)
                				.build()
        );
        
        if (!exists) {
            minioClient.makeBucket(MakeBucketArgs.builder()
            									 .bucket(bucketName)
            									 .build());
        }
    }

    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) 
        	return "";
        return filename.substring(filename.lastIndexOf("."));
    }

}
