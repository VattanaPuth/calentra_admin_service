package valio.admin_service.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import valio.admin_service.dtos.response.RoomResponse;
import valio.admin_service.entities.Room;
import valio.admin_service.mappers.RoomMapper;
import valio.admin_service.services.RoomService;

@RestController
@RequestMapping("/admin/rooms")
@RequiredArgsConstructor
public class RoomController {
	
	private final RoomService roomService;
	private final RoomMapper roomMapper;
	
	/* 
   |=================
   | Guest Operation
   |=================
	*/
	@GetMapping
	public ResponseEntity<List<RoomResponse>> visibleRooms(){
		 List<RoomResponse> rooms = roomService.getAllRoomsVisible();
		 return ResponseEntity.ok(rooms);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Room> getVisibleRoomById(@PathVariable UUID id){
		Room room = roomService.getRoomByIdVisible(id);
		return ResponseEntity.ok(room);
	}
	
	/* 
   |=================
   | ADMIN Operation
   |=================
	*/
	
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<RoomResponse> addRoom(@RequestPart("room") Room request, @RequestPart("image") MultipartFile image){
		Room addedRoom = roomService.addRoom(request, image);
		return ResponseEntity.status(HttpStatus.CREATED).body(roomMapper.toRoomResponse(addedRoom));
	}
	
	@PutMapping(value = "/{roomId}/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<RoomResponse> updateRoom(@PathVariable("roomId")UUID id, @RequestPart("room") Room request, @RequestPart("image") MultipartFile image){
		Room updateRoom = roomService.updateRoom(id, request, image);
		return ResponseEntity.status(HttpStatus.CREATED).body(roomMapper.toRoomResponse(updateRoom));
	}
	
	@PatchMapping("/{id}/visibility")
	public ResponseEntity<Room> updateVisibility(@PathVariable UUID id, @RequestParam Boolean visible) {
		roomService.updateVisibility(id, visible);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{roomId}")
	public ResponseEntity<Void> deleteRoom(@PathVariable("roomId")UUID id){
		roomService.deleteRoom(id);
		return ResponseEntity.noContent().build();	
	}
}
