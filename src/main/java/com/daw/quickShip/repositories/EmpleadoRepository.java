package com.daw.quickShip.repositories;

import com.daw.quickShip.entities.Empleado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    @Query("SELECT e FROM Empleado e WHERE e.active = :isActive")
    Page<Empleado> findByIsActive(boolean isActive, Pageable pageable);
    Optional<Empleado> findByDni(String dni);

    boolean existsByDni(String dni);

    boolean existsByEmail(String email);

}
