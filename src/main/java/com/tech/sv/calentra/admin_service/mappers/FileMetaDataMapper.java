package com.tech.sv.calentra.admin_service.mappers;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import com.tech.sv.calentra.admin_service.dtos.response.FileMetadataResponse;
import com.tech.sv.calentra.admin_service.entities.FileMetadata;

@Mapper(componentModel = "spring")
public interface FileMetaDataMapper {
	public FileMetadataResponse toFileMetadataResponse(FileMetadata fileMetadata);
}
