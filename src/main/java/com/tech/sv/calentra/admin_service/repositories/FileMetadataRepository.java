package com.tech.sv.calentra.admin_service.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tech.sv.calentra.admin_service.entities.FileMetadata;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, UUID>{

}
