package com.tech.sv.calentra.admin_service.services;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.tech.sv.calentra.admin_service.entities.Room;

public interface RoomService {
	Room addRoom(Room room, MultipartFile image);
	Room updateRoom(UUID roomId, Room room, MultipartFile image);
}
