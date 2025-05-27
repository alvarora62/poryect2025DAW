package com.daw.quickShip.repositories;

import com.daw.quickShip.entities.Repartidor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepartidorRepository extends JpaRepository<Repartidor, Long> {

    @Query("SELECT r FROM Repartidor r WHERE r.active = :isActive")
    Page<Repartidor> findByIsActive(boolean isActive, Pageable pageable);

    Optional<Repartidor> findByDni(String dni);

    boolean existsByDni(String dni);

    boolean existsByEmail(String email);
}
