package com.tech.sv.calentra.admin_service.entities;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.tech.sv.calentra.admin_service.enums.FileCategory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "file_metadata")
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FileMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id; //y

    @Column(nullable = false)
    private String originalFileName; //y

    @Column(nullable = false, unique = true)
    private String storedFileName;

    @Column(nullable = false)
    private String bucketName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FileCategory category; //y

    @Column(nullable = false)
    private String contentType; //y

    @Column(nullable = false)
    private long size; //y

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant uploadedAt; //y
}