package com.daw.quickShip.controllers;

import com.daw.quickShip.DTOs.AnotacionRequest;
import com.daw.quickShip.entities.Auditoria;
import com.daw.quickShip.servicies.AuditoriaServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Auditoría", description = "Operaciones relacionadas con el historial de acciones del sistema")
@RestController
@RequestMapping("/auditoria")
@RequiredArgsConstructor
public class AuditoriaController {

    private final AuditoriaServiceImpl auditoriaService;

    @Operation(
            summary = "Obtener historial de auditoría",
            description = "Devuelve un historial paginado de acciones registradas. "
                    + "Usa parámetros `page`, `size`, y `sort` para controlar la respuesta. "
                    + "Ejemplo: `GET /auditoria/historial?page=0&size=1&sort=fecha`"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historial obtenido correctamente")
    })
    @GetMapping("/historial")
    public Page<Auditoria> getHistorial(
            @Parameter(description = "Configuración de paginación y ordenamiento") Pageable pageable
    ) {
        return auditoriaService.verHistorial(pageable);
    }

    @Operation(
            summary = "Anotar acción",
            description = "Registra una nueva acción en el historial. "
                    + "Requiere un objeto JSON con `username` y `accion`."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anotación registrada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida (campos vacíos o malformados)")
    })
    @PostMapping("/anotar")
    public ResponseEntity<Void> realizarAnotacion(
            @Valid @RequestBody AnotacionRequest request
    ) {
        auditoriaService.realizarAnotacion(request.username(), request.accion());
        return ResponseEntity.ok().build();
    }
}
