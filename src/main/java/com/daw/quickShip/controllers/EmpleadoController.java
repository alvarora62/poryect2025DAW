package com.daw.quickShip.controllers;

import com.daw.quickShip.DTOs.RegisterEmpleadoDTO;
import com.daw.quickShip.entities.Empleado;
import com.daw.quickShip.servicies.EmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/empleados")
@Tag(name = "Empleado", description = "API for managing empleados")
@RequiredArgsConstructor
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @GetMapping
    @Operation(summary = "List all empleados", description = "Retrieves a paginated list of all empleados.")
    public Page<Empleado> listAll(Pageable pageable) {
        return empleadoService.listAll(pageable);
    }

    @PostMapping
    @Operation(summary = "Create a new empleado", description = "Creates a new empleado.")
    public ResponseEntity<Void> create(@Valid @RequestBody RegisterEmpleadoDTO registerEmpleadoDTO) {
        return empleadoService.create(registerEmpleadoDTO);
    }

    @PostMapping("/{dni}")
    @Operation(summary = "Update an existing empleado", description = "Updates an existing empleado by DNI.")
    public ResponseEntity<Void> update(
            @PathVariable String dni,
            @Valid @RequestBody RegisterEmpleadoDTO registerEmpleadoDTO
    ) {
        return empleadoService.update(dni, registerEmpleadoDTO);
    }

    @PatchMapping("/status")
    @Operation(summary = "Change empleado active status", description = "Updates the active status of an empleado.")
    public ResponseEntity<Void> changeActiveStatus(@RequestParam String dniEmpleado, @RequestParam boolean active) {
        return empleadoService.changeActiveStatus(dniEmpleado, active);
    }
}
