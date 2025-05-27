package com.daw.quickShip.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for login in as a user.
 */
@Schema(description = "Datos necesarios para iniciar sesion en el sistema")
public record LoginDTO(
    @Schema(description = "Nombre de usuario")
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    String username,

    @Schema(description = "Contraseña del usuario")
    @NotBlank(message = "La contraseña no puede estar vacía")
    String password
)
        { }
