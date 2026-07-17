package valio.admin_service.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import valio.admin_service.entities.FileMetadata;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, UUID>{

}
