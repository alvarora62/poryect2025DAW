package com.daw.quickShip.controllers;

import com.daw.quickShip.entities.Repartidor;
import com.daw.quickShip.servicies.RepartidorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            summary = "Listar repartidores activos",
            description = "Devuelve una lista paginada de repartidores activos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de repartidores activos obtenida correctamente")
    })
    @GetMapping("/active")
    public Page<Repartidor> listAllActive(@Parameter(description = "Configuración de paginación") Pageable pageable) {
        return repartidorService.listAllActive(pageable);
    }

    @Operation(
            summary = "Listar repartidores inactivos",
            description = "Devuelve una lista paginada de repartidores inactivos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de repartidores inactivos obtenida correctamente")
    })
    @GetMapping("/inactive")
    public Page<Repartidor> listAllNotActive(@Parameter(description = "Configuración de paginación") Pageable pageable) {
        return repartidorService.listAllNotActive(pageable);
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
    public ResponseEntity<Void> save(@RequestBody Repartidor repartidor) {
        return repartidorService.save(repartidor);
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
    public ResponseEntity<Void> changeActiveStatus(@RequestBody Repartidor repartidor, @RequestParam boolean isActive) {
        return repartidorService.changeActiveStatus(repartidor, isActive);
    }
}