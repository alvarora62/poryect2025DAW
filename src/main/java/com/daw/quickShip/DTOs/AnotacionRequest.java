package com.daw.quickShip.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for creating a new audit entry.
 */
@Schema(description = "Datos necesarios para registrar una acción en el historial de auditoría")
public record AnotacionRequest(

        @Schema(description = "Nombre de usuario que realiza la acción", example = "juan_perez")
        @NotBlank(message = "El nombre de usuario no puede estar vacío")
        String username,

        @Schema(description = "Descripción de la acción realizada", example = "CREAR_PEDIDO")
        @NotBlank(message = "La acción no puede estar vacía")
        String accion

) {}
