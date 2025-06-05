package com.daw.quickShip.servicies;

import com.daw.quickShip.DTOs.RegisterCredencialesDTO;
import com.daw.quickShip.DTOs.RegisterEmpleadoDTO;
import com.daw.quickShip.DTOs.SelectEmpleadoDTO;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl implements EmpleadoService {

    private final AuditoriaServiceImpl auditoriaService;
    private final EmpleadoRepository empleadoRepository;
    private final CredencialesServiceImpl credencialesService;

    /**
     * Retrieves a paginated list of all employees.
     *
     * @param pageable pagination and sorting information.
     * @return a {@link Page} of {@link Empleado} objects.
     */
    @Override
    public Page<Empleado> listAll(Pageable pageable) {
        return empleadoRepository.findAll(pageable);
    }

    /**
     * Returns a simplified list of employees for selection UI components.
     *
     * @return a list of {@link SelectEmpleadoDTO} containing ID and name.
     */
    @Override
    public List<SelectEmpleadoDTO> selectList() {
        return empleadoRepository.findAll().stream()
                .map(empleado -> new SelectEmpleadoDTO(empleado.getId(), empleado.getNombre()))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves an employee by their ID.
     *
     * @param idEmpleado the ID of the employee.
     * @return the {@link Empleado} if found.
     * @throws ResourceNotFoundException if the employee does not exist.
     */
    @Override
    public Empleado findById(Long idEmpleado) {
        return empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado not found"));
    }

    /**
     * Creates a new employee with the given data and registers their credentials.
     *
     * @param registerEmpleadoDTO the data to create the new employee.
     * @return {@link ResponseEntity#ok()} if creation is successful.
     * @throws FormatException if the email or DNI already exists.
     */
    @Override
    @Transactional
    public ResponseEntity<Void> create(RegisterEmpleadoDTO registerEmpleadoDTO) {

        // Validar campos
        EmpleadoValidator.validateEmpleadoData(registerEmpleadoDTO);

        // Comprobar si el DNI ya existe
        if (empleadoRepository.existsByDni(registerEmpleadoDTO.dni())) {
            throw new FormatException("DNI already exists.");
        }

        // Comprobar si el email ya existe
        if (empleadoRepository.existsByEmail(registerEmpleadoDTO.email())) {
            throw new FormatException("email already exists.");
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
                        registerEmpleadoDTO.username(),
                        registerEmpleadoDTO.password(),
                        savedEmpleado,
                        null
                )
        );

        auditoriaService.realizarAnotacion("admin", "Creado nuevo empleado con ID: " + empleado.getId());
        return ResponseEntity.ok().build();
    }

    /**
     * Updates an existing employee identified by their DNI with new data.
     *
     * @param dni the unique DNI of the employee to update.
     * @param registerEmpleadoDTO the new employee data.
     * @return {@link ResponseEntity#ok()} if the update is successful.
     * @throws ResourceNotFoundException if the employee is not found.
     */
    @Override
    @Transactional
    public ResponseEntity<Void> update(String dni, RegisterEmpleadoDTO registerEmpleadoDTO) {

        // Validar campos
        EmpleadoValidator.validateEmpleadoData(registerEmpleadoDTO);

        Empleado existingEmpleado = empleadoRepository.findByDni(dni)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado with DNI " + dni + " not found."));

        // Actualizar campos permitidos
        existingEmpleado.setNombre(registerEmpleadoDTO.nombre());
        existingEmpleado.setTelefono(registerEmpleadoDTO.telefono());
        existingEmpleado.setEmail(registerEmpleadoDTO.email());

        empleadoRepository.save(existingEmpleado);

        auditoriaService.realizarAnotacion("admin", "Actualizado empleado con ID: " + existingEmpleado.getId());
        return ResponseEntity.ok().build();
    }

    /**
     * Changes the active status of an employee based on their DNI.
     *
     * @param dniEmpleado the DNI of the employee.
     * @param isActive the new active status.
     * @return {@link ResponseEntity#ok()} if the status change is successful.
     * @throws EntityNotFoundException if the employee is not found.
     */
    @Override
    @Transactional
    public ResponseEntity<Void> changeActiveStatus(String dniEmpleado, boolean isActive) {
        Empleado empleado = empleadoRepository.findByDni(dniEmpleado)
                .orElseThrow(() -> new EntityNotFoundException("Empleado not found with DNI: " + dniEmpleado));

        empleado.setActive(isActive);
        empleadoRepository.save(empleado);

        String action = isActive ? "Activado empleado con ID: " + empleado.getId() : "Desactivado empleado con ID: " + empleado.getDni();
        auditoriaService.realizarAnotacion("admin", action);
        return ResponseEntity.ok().build();
    }

}

