package com.daw.quickShip.utils;

import com.daw.quickShip.DTOs.RegisterRepartidorDTO;
import com.daw.quickShip.entities.Repartidor;
import com.daw.quickShip.exceptions.FormatException;

public class RepartidorValidator {

    /**
     * Validates the {@link RegisterRepartidorDTO} and thrown a {@link FormatException} if anything is incorrect
     * @param dto the {@link Repartidor} to check
     */
    public static void validateRepartidorData(RegisterRepartidorDTO dto) {
        if (dto.nombre() == null || dto.nombre().isBlank()) {
            throw new FormatException("El nombre no puede estar vacío.");
        }

        if (dto.dni() == null || !dto.dni().matches("\\d{8}[A-HJ-NP-TV-Z]")) {
            throw new FormatException("El DNI tiene un formato inválido.");
        }

        if (dto.email() == null || !dto.email().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new FormatException("El email tiene un formato inválido.");
        }

        if (dto.telefono() == null || !dto.telefono().matches("\\d{9}")) {
            throw new FormatException("El teléfono tiene un formato inválido.");
        }
    }
}
