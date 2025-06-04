package com.daw.quickShip.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record RegisterRepartidorDTO(

        @NotBlank(message = "El nombre no puede estar vacío.")
        String nombre,

        @NotBlank(message = "El DNI no puede estar vacío.")
        @Pattern(regexp = "\\d{8}[A-HJ-NP-TV-Z]", message = "Formato de DNI inválido.")
        String dni,

        @NotBlank(message = "El email no puede estar vacío.")
        @Email(message = "Formato de email inválido.")
        String email,

        @NotBlank(message = "El teléfono no puede estar vacío.")
        @Pattern(regexp = "\\d{9}", message = "Formato de teléfono inválido.")
        String telefono,

        @NotEmpty
        String nombreEmpresa,

        @NotEmpty(message = "El username no puede estar vacío")
        String username,

        @NotEmpty(message = "La password no puede estar vacío")
        String password

) {}
