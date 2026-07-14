package com.tech.sv.calentra.admin_service.dtos.response;

import java.time.Instant;
import java.util.UUID;

import lombok.Data;

@Data
public class FileMetadataResponse{
	private UUID id;
	private String originalFileName;
	private String storedFileName;
	private String category;
	private String contentType;
	private long size;
	private Instant uploadedAt;
}