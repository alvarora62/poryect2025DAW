package com.daw.quickShip.repositories;

import com.daw.quickShip.entities.Credenciales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredencialesRepository extends JpaRepository<Credenciales, Long> {
}
