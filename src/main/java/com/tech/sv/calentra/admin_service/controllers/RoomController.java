package com.tech.sv.calentra.admin_service.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tech.sv.calentra.admin_service.dtos.response.RoomResponse;
import com.tech.sv.calentra.admin_service.entities.Room;
import com.tech.sv.calentra.admin_service.mappers.RoomMapper;
import com.tech.sv.calentra.admin_service.services.RoomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/rooms")
@RequiredArgsConstructor
public class RoomController {
	
	private final RoomService roomService;
	private final RoomMapper roomMapper;
	
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<RoomResponse> addRoom(@RequestPart("room") Room request, @RequestPart("image") MultipartFile image){
		Room addedRoom = roomService.addRoom(request, image);
		return ResponseEntity.status(HttpStatus.CREATED).body(roomMapper.toRoomResponse(addedRoom));
	}
	
	@PutMapping(value = "/update/{roomId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<RoomResponse> updateRoom(@PathVariable("roomId")UUID id, @RequestPart("room") Room request, @RequestPart("image") MultipartFile image){
		Room updateRoom = roomService.updateRoom(id, request, image);
		return ResponseEntity.status(HttpStatus.CREATED).body(roomMapper.toRoomResponse(updateRoom));
	}
}
