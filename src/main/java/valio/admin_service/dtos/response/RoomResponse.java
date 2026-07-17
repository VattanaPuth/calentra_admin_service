package valio.admin_service.dtos.response;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;
import valio.admin_service.enums.RoomBedType;
import valio.admin_service.enums.RoomStatus;
import valio.admin_service.enums.RoomType;

@Data
public class RoomResponse {
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
