package com.daw.quickShip.repositories;

import com.daw.quickShip.entities.Repartidor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RepartidorRepository extends JpaRepository<Repartidor, Long> {

    @Query("SELECT e FROM Repartidor e WHERE e.active = :isActive")
    Page<Repartidor> findByIsActive(boolean isActive, Pageable pageable);
}
