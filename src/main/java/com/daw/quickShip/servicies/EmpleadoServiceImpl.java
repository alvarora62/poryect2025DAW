package com.daw.quickShip.servicies;

import com.daw.quickShip.entities.Empleado;
import com.daw.quickShip.repositories.EmpleadoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    @Override
    public Page<Empleado> listAll(Pageable pageable) {
        return empleadoRepository.findAll(pageable);
    }

    @Override
    public Page<Empleado> listAllActive(Pageable pageable) {
        return empleadoRepository.findByIsActive(true, pageable);
    }

    @Override
    public Page<Empleado> listAllNotActive(Pageable pageable) {
        return empleadoRepository.findByIsActive(false, pageable);
    }

    @Override
    public ResponseEntity<Void> save(Empleado empleado) {
        empleadoRepository.save(empleado);
        return ResponseEntity.ok().build();
    }

    @Override
    @Transactional
    public ResponseEntity<Void> changeActiveStatus(String dniEmpleado, boolean isActive) {
        Empleado empleado = empleadoRepository.findByDni(dniEmpleado)
                .orElseThrow(() -> new EntityNotFoundException("Empleado not found with DNI: " + dniEmpleado));

        empleado.setActive(isActive);
        empleadoRepository.save(empleado);

        return ResponseEntity.ok().build();
    }

}

