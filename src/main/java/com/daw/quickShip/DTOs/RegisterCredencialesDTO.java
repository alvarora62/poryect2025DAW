package com.daw.quickShip.DTOs;

import com.daw.quickShip.entities.Empleado;
import com.daw.quickShip.entities.Repartidor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for creating a new user.
 */
@Schema(description = "Datos necesarios para registrar un usuario en el sistema")
public record RegisterCredencialesDTO(
        @Schema(description = "Nombre de usuario")
        @NotBlank(message = "El nombre de usuario no puede estar vacío")
        String username,

        @Schema(description = "Contraseña del usuario")
        @NotBlank(message = "La contraseña no puede estar vacía")
        String password,

        Empleado empleado,
        Repartidor repartidor
)
{ }