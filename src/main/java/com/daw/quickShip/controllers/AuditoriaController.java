package com.daw.quickShip.controllers;

import com.daw.quickShip.DTOs.AnotacionRequest;
import com.daw.quickShip.entities.Auditoria;
import com.daw.quickShip.servicies.AuditoriaServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auditoría", description = "Operaciones relacionadas con el historial de acciones del sistema")
@RestController
@RequestMapping("/api/auditoria")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuditoriaController {

    private final AuditoriaServiceImpl auditoriaService;

    @GetMapping("/historial")
    @Operation(summary = "Obtener historial de auditoría",
            description = "Devuelve un historial paginado de acciones registradas. "
                    + "Usa parámetros `page`, `size`, y `sort` para controlar la respuesta. "
                    + "Ejemplo: `GET /auditoria/historial?page=0&size=1&sort=fecha`"
    )
    public Page<Auditoria> getHistorial(Pageable pageable) {
        return auditoriaService.verHistorial(pageable);
    }

    @PostMapping("/anotar")
    @Operation(summary = "Anotar acción", description = "Registra una nueva acción en el historial. " + "Requiere un objeto JSON con `username` y `accion`.")
    public ResponseEntity<Void> realizarAnotacion(@Valid @RequestBody AnotacionRequest request) {
        auditoriaService.realizarAnotacion(request.username(), request.accion());
        return ResponseEntity.ok().build();
    }
}
