package com.daw.quickShip.controllers;

import com.daw.quickShip.entities.Empleado;
import com.daw.quickShip.servicies.EmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/active")
    @Operation(summary = "List all active empleados", description = "Retrieves a paginated list of active empleados.")
    public Page<Empleado> listAllActive(Pageable pageable) {
        return empleadoService.listAllActive(pageable);
    }

    @GetMapping("/inactive")
    @Operation(summary = "List all inactive empleados", description = "Retrieves a paginated list of inactive empleados.")
    public Page<Empleado> listAllNotActive(Pageable pageable) {
        return empleadoService.listAllNotActive(pageable);
    }

    @PostMapping
    @Operation(summary = "Save or update an empleado", description = "Creates or updates an empleado.")
    public ResponseEntity<Void> save(@RequestBody Empleado empleado) {
        return empleadoService.save(empleado);
    }

    @PatchMapping("/status")
    @Operation(summary = "Change empleado active status", description = "Updates the active status of an empleado.")
    public ResponseEntity<Void> changeActiveStatus(@RequestBody Empleado empleado, @RequestParam boolean isActive) {
        return empleadoService.changeActiveStatus(empleado, isActive);
    }
}

