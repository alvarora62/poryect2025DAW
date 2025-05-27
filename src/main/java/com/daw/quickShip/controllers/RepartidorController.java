package com.daw.quickShip.controllers;

import com.daw.quickShip.DTOs.RegisterEmpleadoDTO;
import com.daw.quickShip.DTOs.RegisterRepartidorDTO;
import com.daw.quickShip.entities.Repartidor;
import com.daw.quickShip.servicies.RepartidorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@Tag(name = "Repartidor", description = "API para gestionar repartidores")
@RestController
@RequestMapping("/api/repartidores")
@RequiredArgsConstructor
public class RepartidorController {

    private final RepartidorService repartidorService;

    @Operation(
            summary = "Listar todos los repartidores",
            description = "Devuelve una lista paginada de todos los repartidores."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de repartidores obtenida correctamente")
    })
    @GetMapping
    public Page<Repartidor> listAll(@Parameter(description = "Configuración de paginación") Pageable pageable) {
        return repartidorService.listAll(pageable);
    }

    @Operation(
            summary = "Guardar o actualizar un repartidor",
            description = "Guarda o actualiza un repartidor."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Repartidor guardado o actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida (campos vacíos o malformados)")
    })
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody RegisterRepartidorDTO registerRepartidorDTO) {
        return repartidorService.create(registerRepartidorDTO);
    }
    @PostMapping("/{dni}")
    @Operation(summary = "Update an existing repartidor", description = "Updates an existing repartidor by DNI.")
    public ResponseEntity<Void> update(
            @PathVariable String dni,
            @Valid @RequestBody RegisterRepartidorDTO registerRepartidorDTO
    ) {
        return repartidorService.update(dni, registerRepartidorDTO);
    }

    @Operation(
            summary = "Cambiar estado de actividad de un repartidor",
            description = "Actualiza el estado de actividad de un repartidor."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Repartidor no encontrado")
    })
    @PatchMapping("/status")
    public ResponseEntity<Void> changeActiveStatus(@RequestParam String dni, @RequestParam boolean active) {
        return repartidorService.changeActiveStatus(dni, active);
    }
}