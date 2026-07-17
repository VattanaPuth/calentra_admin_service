package valio.admin_service.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import valio.admin_service.entities.Room;

public interface RoomRepository extends JpaRepository<Room, UUID>{
	Optional<Room> findById(UUID id);
	
    @Query(value = "SELECT * FROM rooms WHERE id = :id", nativeQuery = true)
    Optional<Room> findByIdIncludingHidden(@Param("id") UUID id);
    
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE rooms SET visible = :visible WHERE id = :id", nativeQuery = true)
	void updateVisibility(@Param("id") UUID roomId, @Param("visible") Boolean visible);
	
    @Query("SELECT r FROM Room r LEFT JOIN FETCH r.image WHERE r.visible = true")
    List<Room> findAllVisibleWithImage();
    
    @Query("SELECT r FROM Room r LEFT JOIN FETCH r.image WHERE r.visible = true AND r.id = :id")
    Optional<Room> findVisibleWithImageById(UUID id);
}
