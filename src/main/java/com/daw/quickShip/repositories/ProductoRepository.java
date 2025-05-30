package com.daw.quickShip.repositories;

import com.daw.quickShip.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}