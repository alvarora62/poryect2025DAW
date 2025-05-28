package com.daw.quickShip.servicies;

import com.daw.quickShip.entities.Empleado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface PedidoService {

    Page<Empleado> listAll(Pageable pageable);

    ResponseEntity<Void> setEmpleado(Long id);
    ResponseEntity<Void> setRepartidor(Long id);
    ResponseEntity<Void> changeActiveStatus(String status);
}
