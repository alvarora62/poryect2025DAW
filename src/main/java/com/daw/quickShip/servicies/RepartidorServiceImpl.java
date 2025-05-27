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

@Service
@RequiredArgsConstructor
public class RepartidorServiceImpl implements RepartidorService {

    private final RepartidorRepository repartidorRepository;
    private final CredencialesServiceImpl credencialesService;

    @Override
    public Page<Repartidor> listAll(Pageable pageable) {
        return repartidorRepository.findAll(pageable);
    }

    @Override
    public ResponseEntity<Void> create(RegisterRepartidorDTO registerRepartidorDTO) {
        // Validar campos
        RepartidorValidator.validateRepartidorData(registerRepartidorDTO);

        // Comprobar si el DNI ya existe
        if (repartidorRepository.existsByDni(registerRepartidorDTO.dni())) {
            throw new FormatException("El DNI ya está registrado en el sistema.");
        }

        // Comprobar si el email ya existe
        if (repartidorRepository.existsByEmail(registerRepartidorDTO.email())) {
            throw new FormatException("El email ya está registrado en el sistema.");
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
                        registerRepartidorDTO.email(),
                        registerRepartidorDTO.dni(),
                        null,
                        savedRepartidor
                )
        );

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> update(String dni, RegisterRepartidorDTO registerRepartidorDTO) {
        // Validar campos
        RepartidorValidator.validateRepartidorData(registerRepartidorDTO);

        Repartidor existingRepartidor = repartidorRepository.findByDni(dni)
                .orElseThrow(() -> new ResourceNotFoundException("Repartidor con DNI " + dni + " no encontrado."));

        existingRepartidor.setNombre(registerRepartidorDTO.nombre());
        existingRepartidor.setTelefono(registerRepartidorDTO.telefono());
        existingRepartidor.setEmail(registerRepartidorDTO.email());

        repartidorRepository.save(existingRepartidor);

        return ResponseEntity.ok().build();
    }

    @Override
    @Transactional
    public ResponseEntity<Void> changeActiveStatus(String dniRepartidor, boolean isActive) {
        Repartidor repartidor = repartidorRepository.findByDni(dniRepartidor)
                .orElseThrow(() -> new EntityNotFoundException("Repartidor no encontrado con DNI: " + dniRepartidor));

        repartidor.setActive(isActive);
        repartidorRepository.save(repartidor);

        return ResponseEntity.ok().build();
    }
}
