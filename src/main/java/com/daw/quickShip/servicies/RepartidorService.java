package com.daw.quickShip.servicies;

import com.daw.quickShip.DTOs.RegisterRepartidorDTO;
import com.daw.quickShip.entities.Repartidor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface RepartidorService {

    Page<Repartidor> listAll(Pageable pageable);

    ResponseEntity<Void> create(RegisterRepartidorDTO registerRepartidorDTO);
    ResponseEntity<Void> update(String dni, RegisterRepartidorDTO registerRepartidorDTO);
    ResponseEntity<Void> changeActiveStatus(String dniRepartidor, boolean isActive);
}
