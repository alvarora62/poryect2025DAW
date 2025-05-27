package com.daw.quickShip.servicies;

import com.daw.quickShip.DTOs.RegisterCredencialesDTO;
import com.daw.quickShip.DTOs.RegisterEmpleadoDTO;
import com.daw.quickShip.exceptions.FormatException;
import com.daw.quickShip.entities.Empleado;
import com.daw.quickShip.exceptions.ResourceNotFoundException;
import com.daw.quickShip.repositories.EmpleadoRepository;
import com.daw.quickShip.utils.EmpleadoValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final CredencialesServiceImpl credencialesService;

    @Override
    public Page<Empleado> listAll(Pageable pageable) {
        return empleadoRepository.findAll(pageable);
    }

    @Override
    public ResponseEntity<Void> create(RegisterEmpleadoDTO registerEmpleadoDTO) {

        // Validar campos
        EmpleadoValidator.validateEmpleadoData(registerEmpleadoDTO);

        // Comprobar si el DNI ya existe
        if (empleadoRepository.existsByDni(registerEmpleadoDTO.dni())) {
            throw new FormatException("El DNI ya está registrado en el sistema.");
        }

        // Comprobar si el email ya existe
        if (empleadoRepository.existsByEmail(registerEmpleadoDTO.email())) {
            throw new FormatException("El email ya está registrado en el sistema.");
        }

        // Crear nuevo empleado
        Empleado empleado = Empleado.builder()
                .nombre(registerEmpleadoDTO.nombre())
                .dni(registerEmpleadoDTO.dni())
                .email(registerEmpleadoDTO.email())
                .telefono(registerEmpleadoDTO.telefono())
                .build();

        Empleado savedEmpleado = empleadoRepository.save(empleado);

        // Registrar credenciales
        credencialesService.registrar(
                new RegisterCredencialesDTO(
                        registerEmpleadoDTO.email(),
                        registerEmpleadoDTO.dni(),
                        savedEmpleado,
                        null
                )
        );

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> update(String dni, RegisterEmpleadoDTO registerEmpleadoDTO) {

        // Validar campos
        EmpleadoValidator.validateEmpleadoData(registerEmpleadoDTO);

        Empleado existingEmpleado = empleadoRepository.findByDni(dni)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado con DNI " + dni + " no encontrado."));

        // Actualizar campos permitidos
        existingEmpleado.setNombre(registerEmpleadoDTO.nombre());
        existingEmpleado.setTelefono(registerEmpleadoDTO.telefono());
        existingEmpleado.setEmail(registerEmpleadoDTO.email());

        empleadoRepository.save(existingEmpleado);

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

