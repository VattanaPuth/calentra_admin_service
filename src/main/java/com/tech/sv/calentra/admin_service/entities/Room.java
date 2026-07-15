package com.tech.sv.calentra.admin_service.entities;

import java.math.BigDecimal;
import java.util.UUID;

import org.hibernate.annotations.SQLRestriction;

import com.tech.sv.calentra.admin_service.enums.RoomBedType;
import com.tech.sv.calentra.admin_service.enums.RoomStatus;
import com.tech.sv.calentra.admin_service.enums.RoomType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "rooms")
@SQLRestriction("visible = true")
@Data
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	private long roomNumber;
	private int floor;
	private int capacity;
	private int size;
	private BigDecimal price;
	
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private FileMetadata image;
	
	@Enumerated(EnumType.STRING)
	private RoomStatus roomStatus;
	
	@Enumerated(EnumType.STRING)
	private RoomType roomType;
	
	@Enumerated(EnumType.STRING)
	private RoomBedType bedType;
	
	private Boolean visible = true;
}
