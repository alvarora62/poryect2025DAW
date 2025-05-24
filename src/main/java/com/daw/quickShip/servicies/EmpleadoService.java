package com.daw.quickShip.servicies;

import com.daw.quickShip.entities.Empleado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface EmpleadoService {

    Page<Empleado> listAll(Pageable pageable);
    Page<Empleado> listAllActive(Pageable pageable);
    Page<Empleado> listAllNotActive(Pageable pageable);

    ResponseEntity<Void> save(Empleado empleado);
    ResponseEntity<Void> changeActiveStatus(Empleado empleado, boolean isActive);
}
