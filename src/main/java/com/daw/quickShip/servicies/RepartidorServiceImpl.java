package com.daw.quickShip.servicies;

import com.daw.quickShip.DTOs.RegisterCredencialesDTO;
import com.daw.quickShip.DTOs.RegisterRepartidorDTO;
import com.daw.quickShip.entities.Repartidor;
import com.daw.quickShip.exceptions.FormatException;
import com.daw.quickShip.exceptions.ResourceNotFoundException;
import com.daw.quickShip.repositories.RepartidorRepository;
import com.daw.quickShip.utils.RepartidorValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RepartidorServiceImpl implements RepartidorService {

    private final RepartidorRepository repartidorRepository;
    private final CredencialesServiceImpl credencialesService;
    private final AuditoriaServiceImpl auditoriaService;

    @Override
    public Page<Repartidor> listAll(Pageable pageable) {
        return repartidorRepository.findAll(pageable);
    }

    @Override
    public List<String> selectList() {
        return repartidorRepository.findDistinctNombreEmpresa();
    }

    @Override
    public List<Repartidor> findByNombreEmpresa(String nombreEmpresa) {
        return repartidorRepository.findByNombreEmpresa(nombreEmpresa);
    }

    @Override
    @Transactional
    public ResponseEntity<Void> create(RegisterRepartidorDTO registerRepartidorDTO) {
        // Validar campos
        RepartidorValidator.validateRepartidorData(registerRepartidorDTO);

        // Comprobar si el DNI ya existe
        if (repartidorRepository.existsByDni(registerRepartidorDTO.dni())) {
            throw new FormatException("DNI already exists.");
        }

        // Comprobar si el email ya existe
        if (repartidorRepository.existsByEmail(registerRepartidorDTO.email())) {
            throw new FormatException("Email already exists.");
        }

        // Crear nuevo repartidor
        Repartidor repartidor = Repartidor.builder()
                .nombre(registerRepartidorDTO.nombre())
                .dni(registerRepartidorDTO.dni())
                .email(registerRepartidorDTO.email())
                .telefono(registerRepartidorDTO.telefono())
                .build();

        Repartidor savedRepartidor = repartidorRepository.save(repartidor);

        // Registrar credenciales
        credencialesService.registrar(
                new RegisterCredencialesDTO(
                        registerRepartidorDTO.username(),
                        registerRepartidorDTO.password(),
                        null,
                        savedRepartidor
                )
        );

        auditoriaService.realizarAnotacion("admin", "Creado el repartidor con ID: " + savedRepartidor.getId());
        return ResponseEntity.ok().build();
    }

    @Override
    @Transactional
    public ResponseEntity<Void> update(String dni, RegisterRepartidorDTO registerRepartidorDTO) {
        // Validar campos
        RepartidorValidator.validateRepartidorData(registerRepartidorDTO);

        Repartidor existingRepartidor = repartidorRepository.findByDni(dni)
                .orElseThrow(() -> new ResourceNotFoundException("Repartidor with DNI " + dni + " not found."));

        existingRepartidor.setNombre(registerRepartidorDTO.nombre());
        existingRepartidor.setTelefono(registerRepartidorDTO.telefono());
        existingRepartidor.setEmail(registerRepartidorDTO.email());

        Repartidor updatedRepartidor = repartidorRepository.save(existingRepartidor);
        auditoriaService.realizarAnotacion("admin", "Actualizado el repartidor con ID: " + updatedRepartidor.getId());
        return ResponseEntity.ok().build();
    }

    @Override
    @Transactional
    public ResponseEntity<Void> changeActiveStatus(String dniRepartidor, boolean isActive) {
        Repartidor repartidor = repartidorRepository.findByDni(dniRepartidor)
                .orElseThrow(() -> new EntityNotFoundException("Repartidor with DNI: " + dniRepartidor + " not found."));

        repartidor.setActive(isActive);
        Repartidor updatedRepartidor = repartidorRepository.save(repartidor);
        auditoriaService.realizarAnotacion("admin", "Actualizado el estado del repartidor con ID: " + repartidor.getId());

        return ResponseEntity.ok().build();
    }
}
