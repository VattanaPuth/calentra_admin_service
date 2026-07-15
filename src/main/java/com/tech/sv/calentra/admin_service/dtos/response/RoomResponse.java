package com.tech.sv.calentra.admin_service.dtos.response;

import java.math.BigDecimal;

import com.tech.sv.calentra.admin_service.enums.RoomBedType;
import com.tech.sv.calentra.admin_service.enums.RoomStatus;
import com.tech.sv.calentra.admin_service.enums.RoomType;

import lombok.Data;

@Data
public class RoomResponse {
	private long roomNumber;
    private int floor;
    private int capacity;
    private int size;
    private BigDecimal price;
    private FileMetadataResponse image;
    private RoomStatus roomStatus;
    private RoomType roomType;
    private RoomBedType bedType;
}
