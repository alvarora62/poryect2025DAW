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

    /**
     * Retrieves a paginated list of all repartidores.
     *
     * @param pageable the pagination and sorting information.
     * @return a page of {@link Repartidor} entities.
     */
    @Override
    public Page<Repartidor> listAll(Pageable pageable) {
        return repartidorRepository.findAll(pageable);
    }

    /**
     * Retrieves a distinct list of delivery company names.
     *
     * @return a list of company names ({@link String}).
     */
    @Override
    public List<String> selectList() {
        return repartidorRepository.findDistinctNombreEmpresa();
    }

    /**
     * Retrieves a list of repartidores that belong to the specified company.
     *
     * @param nombreEmpresa the name of the delivery company.
     * @return a list of {@link Repartidor} entities.
     */
    @Override
    public List<Repartidor> findByNombreEmpresa(String nombreEmpresa) {
        return repartidorRepository.findByNombreEmpresa(nombreEmpresa);
    }

    /**
     * Creates and saves a new repartidor based on the provided DTO.
     * Also registers login credentials for the repartidor.
     *
     * @param registerRepartidorDTO the data required to create the repartidor.
     * @return a {@link ResponseEntity} indicating success or failure.
     * @throws FormatException if the DNI or email already exists.
     */
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
                        registerRepartidorDTO.email(),
                        registerRepartidorDTO.dni(),
                        null,
                        savedRepartidor
                )
        );

        return ResponseEntity.ok().build();
    }

    /**
     * Updates the information of an existing repartidor by DNI.
     *
     * @param dni                    the DNI of the repartidor to update.
     * @param registerRepartidorDTO the new data to apply.
     * @return a {@link ResponseEntity} indicating success.
     * @throws ResourceNotFoundException if the repartidor does not exist.
     */
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

        repartidorRepository.save(existingRepartidor);

        return ResponseEntity.ok().build();
    }

    /**
     * Changes the active status of a repartidor by DNI.
     *
     * @param dniRepartidor the DNI of the repartidor.
     * @param isActive      the new active status to apply.
     * @return a {@link ResponseEntity} indicating success.
     * @throws EntityNotFoundException if the repartidor does not exist.
     */
    @Override
    @Transactional
    public ResponseEntity<Void> changeActiveStatus(String dniRepartidor, boolean isActive) {
        Repartidor repartidor = repartidorRepository.findByDni(dniRepartidor)
                .orElseThrow(() -> new EntityNotFoundException("Repartidor with DNI: " + dniRepartidor + " not found."));

        repartidor.setActive(isActive);
        repartidorRepository.save(repartidor);

        return ResponseEntity.ok().build();
    }
}
