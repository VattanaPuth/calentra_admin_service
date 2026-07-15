package com.tech.sv.calentra.admin_service.services.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tech.sv.calentra.admin_service.entities.FileMetadata;
import com.tech.sv.calentra.admin_service.entities.Room;
import com.tech.sv.calentra.admin_service.repositories.FileMetadataRepository;
import com.tech.sv.calentra.admin_service.repositories.RoomRepository;
import com.tech.sv.calentra.admin_service.services.MinioService;
import com.tech.sv.calentra.admin_service.services.RoomService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{
	
	private final RoomRepository roomRepository;
	private final MinioService minioServiceImpl;
	private final FileMetadataRepository fileMetadataRepository;
	private final MinioService minioService;

	@Override
	public Room addRoom(Room room, MultipartFile image) {
		FileMetadata metadata = minioServiceImpl.uploadFile(image);
        FileMetadata savedMetadata = fileMetadataRepository.save(metadata);
        room.setImage(savedMetadata);
		return roomRepository.save(room);
	}

	@Override
	public Room updateRoom(UUID roomId, Room room, MultipartFile image) {
		Room existedRoom = roomRepository.findById(roomId).orElseThrow(RuntimeException::new);
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

}
