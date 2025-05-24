package com.daw.quickShip.servicies;

import com.daw.quickShip.entities.Empleado;
import com.daw.quickShip.repositories.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    public ResponseEntity<Void> changeActiveStatus(Empleado empleado, boolean isActive) {
        Optional<Empleado> existingEmpleado = empleadoRepository.findById(empleado.getId());
        if (existingEmpleado.isPresent()) {
            Empleado updatedEmpleado = existingEmpleado.get();
            updatedEmpleado.setActive(isActive);
            empleadoRepository.save(updatedEmpleado);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}

