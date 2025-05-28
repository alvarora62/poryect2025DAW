package com.daw.quickShip.controllers;

import com.daw.quickShip.DTOs.RegisterRepartidorDTO;
import com.daw.quickShip.entities.Repartidor;
import com.daw.quickShip.servicies.RepartidorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Repartidor", description = "API para gestionar repartidores")
@RestController
@RequestMapping("/api/repartidores")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class RepartidorController {

    private final RepartidorService repartidorService;

    @GetMapping
    @Operation(summary = "Listar todos los repartidores", description = "Devuelve una lista paginada de todos los repartidores.")
    public Page<Repartidor> listAll(@Parameter(description = "Configuración de paginación") Pageable pageable) {
        return repartidorService.listAll(pageable);
    }

    @PostMapping
    @Operation(summary = "Guardar o actualizar un repartidor", description = "Guarda o actualiza un repartidor.")
    public ResponseEntity<Void> save(@RequestBody RegisterRepartidorDTO registerRepartidorDTO) {
        return repartidorService.create(registerRepartidorDTO);
    }

    @PostMapping("/{dni}")
    @Operation(summary = "Update an existing repartidor", description = "Updates an existing repartidor by DNI.")
    public ResponseEntity<Void> update(@PathVariable String dni, @Valid @RequestBody RegisterRepartidorDTO registerRepartidorDTO) {
        return repartidorService.update(dni, registerRepartidorDTO);
    }

    @PatchMapping("/status")
    @Operation(summary = "Cambiar estado de actividad de un repartidor", description = "Actualiza el estado de actividad de un repartidor.")
    public ResponseEntity<Void> changeActiveStatus(@RequestParam String dni, @RequestParam boolean active) {
        return repartidorService.changeActiveStatus(dni, active);
    }
}