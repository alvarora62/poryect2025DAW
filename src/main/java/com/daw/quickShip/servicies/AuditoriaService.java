package com.daw.quickShip.servicies;

import com.daw.quickShip.entities.Auditoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface AuditoriaService {

    Page<Auditoria> verHistorial(Pageable pageable);
    ResponseEntity<Void> realizarAnotacion(String username, String accion);
}