package valio.admin_service.services;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import valio.admin_service.entities.FileMetadata;

public interface MinioService {
	FileMetadata uploadFile(MultipartFile file);
	List<FileMetadata> uploadFiles(List<MultipartFile> files);
	void deleteFile(UUID fileId);
}
