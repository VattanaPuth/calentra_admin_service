package valio.admin_service.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import valio.admin_service.dtos.response.FileMetadataResponse;
import valio.admin_service.entities.FileMetadata;
import valio.admin_service.mappers.FileMetaDataMapper;
import valio.admin_service.services.MinioService;

@RestController
@RequestMapping("/admin/files")
@RequiredArgsConstructor
public class FileController {

	private final MinioService minioServiceImpl;
	private final FileMetaDataMapper dataMapper;

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
		FileMetadata savedMetadata = minioServiceImpl.uploadFile(file);
		FileMetadataResponse response = dataMapper.toFileMetadataResponse(savedMetadata);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<List<FileMetadata>> uploadFiles(@RequestParam("files") List<MultipartFile> files) {
		List<FileMetadata> uploadedFiles = minioServiceImpl.uploadFiles(files);
		return ResponseEntity.status(HttpStatus.CREATED).body(uploadedFiles);
	}

	@DeleteMapping("/delete/{fileId}")
	public ResponseEntity<Void> delete(@PathVariable UUID fileId) {
		minioServiceImpl.deleteFile(fileId);
		return ResponseEntity.noContent().build();
	}
}
