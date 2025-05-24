package com.daw.quickShip.servicies;

import com.daw.quickShip.entities.Repartidor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface RepartidorService {

    Page<Repartidor> listAll(Pageable pageable);
    Page<Repartidor> listAllActive(Pageable pageable);
    Page<Repartidor> listAllNotActive(Pageable pageable);

    ResponseEntity<Void> save(Repartidor repartidor);
    ResponseEntity<Void> changeActiveStatus(Repartidor repartidor, boolean isActive);
}
