package com.daw.quickShip.servicies;

import com.daw.quickShip.DTOs.RegisterRepartidorDTO;
import com.daw.quickShip.DTOs.SelectRepartidorDTO;
import com.daw.quickShip.entities.Repartidor;
import com.daw.quickShip.exceptions.FormatException;
import com.daw.quickShip.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RepartidorService {

    /**
     * Retrieves a paginated list of all repartidores.
     *
     * @param pageable the pagination and sorting information.
     * @return a page of {@link Repartidor} entities.
     */
    Page<Repartidor> listAll(Pageable pageable);

    /**
     * Retrieves a distinct list of delivery company names.
     *
     * @return a list of company names ({@link String}).
     */
    List<String> selectList();

    /**
     * Retrieves a list of repartidores that belong to the specified company.
     *
     * @param nombreEmpresa the name of the delivery company.
     * @return a list of {@link Repartidor} entities.
     */
    List<Repartidor> findByNombreEmpresa(String nombreEmpresa);

    /**
     * Creates and saves a new repartidor based on the provided DTO.
     * Also registers login credentials for the repartidor.
     *
     * @param registerRepartidorDTO the data required to create the repartidor.
     * @return a {@link ResponseEntity} indicating success or failure.
     * @throws FormatException if the DNI or email already exists.
     */
    ResponseEntity<Void> create(RegisterRepartidorDTO registerRepartidorDTO);

    /**
     * Updates the information of an existing repartidor by DNI.
     *
     * @param dni                    the DNI of the repartidor to update.
     * @param registerRepartidorDTO the new data to apply.
     * @return a {@link ResponseEntity} indicating success.
     * @throws ResourceNotFoundException if the repartidor does not exist.
     */
    ResponseEntity<Void> update(String dni, RegisterRepartidorDTO registerRepartidorDTO);

    /**
     * Changes the active status of a repartidor by DNI.
     *
     * @param dniRepartidor the DNI of the repartidor.
     * @param isActive      the new active status to apply.
     * @return a {@link ResponseEntity} indicating success.
     * @throws EntityNotFoundException if the repartidor does not exist.
     */
    ResponseEntity<Void> changeActiveStatus(String dniRepartidor, boolean isActive);
}
