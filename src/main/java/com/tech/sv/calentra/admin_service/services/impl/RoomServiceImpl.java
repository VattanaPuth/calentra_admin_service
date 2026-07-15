package com.tech.sv.calentra.admin_service.services.impl;

import com.tech.sv.calentra.admin_service.mappers.RoomMapperImpl;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tech.sv.calentra.admin_service.dtos.response.RoomResponse;
import com.tech.sv.calentra.admin_service.entities.FileMetadata;
import com.tech.sv.calentra.admin_service.entities.Room;
import com.tech.sv.calentra.admin_service.mappers.RoomMapper;
import com.tech.sv.calentra.admin_service.repositories.FileMetadataRepository;
import com.tech.sv.calentra.admin_service.repositories.RoomRepository;
import com.tech.sv.calentra.admin_service.services.MinioService;
import com.tech.sv.calentra.admin_service.services.RoomService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{
	
	private final RoomMapperImpl roomMapperImpl;
	private final RoomRepository roomRepository;
	private final MinioService minioServiceImpl;
	private final FileMetadataRepository fileMetadataRepository;
	private final MinioService minioService;

	
	private Room getRoomByIdIncludeHidden(UUID id) {
		return roomRepository.findByIdIncludingHidden(id).orElseThrow(RuntimeException::new);
	}
	
	/* 
   |=================
   | Guest Operation
   |=================
	*/
	 
	@Override
	public List<RoomResponse> getAllRoomsVisible() {
	    return roomRepository.findAllVisibleWithImage()
	            .stream()
	            .map(roomMapperImpl::toRoomResponse)
	            .toList();
	    }
	
	@Override
	public Room getRoomByIdVisible(UUID roomId) {
		return roomRepository.findVisibleWithImageById(roomId).orElseThrow(RuntimeException::new);
	}
	
	
	/* 
   |=================
   | ADMIN Operation
   |=================
	*/

	@Override
	public Room addRoom(Room room, MultipartFile image) {
		FileMetadata metadata = minioServiceImpl.uploadFile(image);
        FileMetadata savedMetadata = fileMetadataRepository.save(metadata);
        room.setImage(savedMetadata);
		return roomRepository.save(room);
	}

	@Override
	public Room updateRoom(UUID roomId, Room room, MultipartFile image) {
		Room existedRoom = getRoomByIdIncludeHidden(roomId);
		existedRoom.setRoomNumber(room.getRoomNumber());
		existedRoom.setFloor(room.getFloor());
		existedRoom.setCapacity(room.getCapacity());
		existedRoom.setSize(room.getSize());
		existedRoom.setPrice(room.getPrice());
		existedRoom.setRoomStatus(room.getRoomStatus());
		existedRoom.setRoomType(room.getRoomType());
		existedRoom.setBedType(room.getBedType());
		
	    if (image != null && !image.isEmpty()) {
	    	 FileMetadata oldImage = existedRoom.getImage();
	    	 FileMetadata newImage = minioService.uploadFile(image);
	    	 existedRoom.setImage(newImage);
	    	 roomRepository.saveAndFlush(existedRoom); 
	    	    if (oldImage != null) {
	    	        minioService.deleteFile(oldImage.getId());
	    	    }
	    }

		return roomRepository.save(existedRoom);
	}

	@Transactional
	@Override
	public void updateVisibility(UUID id, Boolean visibility) {
		roomRepository.updateVisibility(id, visibility);
	}

	@Transactional
	@Override
	public void deleteRoom(UUID id) {
		Room room = roomRepository.findByIdIncludingHidden(id).orElseThrow(RuntimeException::new);
        if (room.getImage() != null && room.getImage().getId() != null) {
            minioService.deleteFile(room.getImage().getId());
        }
        roomRepository.delete(room);
	}

}
