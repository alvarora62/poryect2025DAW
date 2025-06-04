package com.daw.quickShip.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

/**
 * DTO for creating a new empleado.
 */
@Schema(description = "Datos necesarios para registrar un empleado en el sistema")
public record RegisterEmpleadoDTO(
        Long id,

        @NotEmpty(message = "El nombre no puede estar vacío")
        String nombre,

        @NotEmpty(message = "El DNI no puede estar vacío")
        @Pattern(regexp = "^[0-9]{8}[A-Z]+$", message = "El dni debe tener un formato especifico")
        String dni,

        @NotEmpty(message = "El teléfono no puede estar vacío")
        @Pattern(regexp = "^[0-9]{9}$", message = "El teléfono debe tener 9 dígitos")
        String telefono,

        @NotEmpty(message = "El email no puede estar vacío")
        @Email(message = "Formato de email inválido")
        String email,

        @NotEmpty(message = "El username no puede estar vacío")
        String username,

        @NotEmpty(message = "La password no puede estar vacío")
        String password
)
{ }
