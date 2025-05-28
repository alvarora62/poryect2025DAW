package com.daw.quickShip.utils;

import com.daw.quickShip.DTOs.RegisterEmpleadoDTO;
import com.daw.quickShip.entities.Empleado;
import com.daw.quickShip.exceptions.FormatException;

public class EmpleadoValidator {

    /**
     * Validates the {@link RegisterEmpleadoDTO} and thrown a {@link FormatException} if anything is incorrect
     * @param dto the {@link Empleado} to check
     */
    public static void validateEmpleadoData(RegisterEmpleadoDTO dto) {
        if (!dto.nombre().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+$")) {
            throw new FormatException("El nombre del empleado no cumple con el formato correcto.");
        }

        if (!dto.dni().matches("^[0-9]{8}[A-Z]$")) {
            throw new FormatException("El DNI del empleado no cumple con el formato correcto.");
        }

        if (!dto.telefono().matches("^[0-9]{9}$")) {
            throw new FormatException("El teléfono debe tener exactamente 9 dígitos.");
        }

        if (!dto.email().matches("^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new FormatException("El email no cumple con el formato esperado.");
        }
    }

}
