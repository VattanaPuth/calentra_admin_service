package valio.admin_service.mappers;

import org.mapstruct.Mapper;

import valio.admin_service.dtos.response.FileMetadataResponse;
import valio.admin_service.entities.FileMetadata;

@Mapper(componentModel = "spring")
public interface FileMetaDataMapper {
	public FileMetadataResponse toFileMetadataResponse(FileMetadata fileMetadata);
}
