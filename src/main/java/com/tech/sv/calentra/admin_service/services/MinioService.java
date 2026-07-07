package com.tech.sv.calentra.admin_service.services;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.tech.sv.calentra.admin_service.entities.FileMetadata;

public interface MinioService {
	FileMetadata uploadFile(MultipartFile file);
	void deleteFile(UUID fileId);
}
