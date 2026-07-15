package com.tech.sv.calentra.admin_service.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


import com.tech.sv.calentra.admin_service.entities.Room;

public interface RoomRepository extends JpaRepository<Room, UUID>{
	Optional<Room> findById(UUID id);
}
