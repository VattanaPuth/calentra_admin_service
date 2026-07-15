package com.tech.sv.calentra.admin_service.dtos.request;

import java.math.BigDecimal;
import java.util.UUID;

import com.tech.sv.calentra.admin_service.dtos.response.FileMetadataResponse;
import com.tech.sv.calentra.admin_service.enums.RoomBedType;
import com.tech.sv.calentra.admin_service.enums.RoomStatus;
import com.tech.sv.calentra.admin_service.enums.RoomType;

import lombok.Data;

@Data
public class RoomRequest {
	private UUID id;
	private long roomNumber;
    private int floor;
    private int capacity;
    private int size;
    private BigDecimal price;
    private FileMetadataResponse image;
    private RoomStatus roomStatus;
    private RoomType roomType;
    private RoomBedType bedType;
    private Boolean visibility;
}
