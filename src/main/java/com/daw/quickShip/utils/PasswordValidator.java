package com.daw.quickShip.utils;

import com.daw.quickShip.exceptions.FormatException;

public class PasswordValidator {
    /**
     * Valida la contraseña según los siguientes criterios:
     * - Debe ser de al menos 8 caracteres.
     * - Debe contener al menos una letra mayúscula.
     * - Debe contener al menos un número.
     * - Debe contener al menos un carácter especial.
     * - No debe contener espacios.
     *
     * @param password La contraseña a validar.
     * @throws FormatException if the password is not valid
     */
    public static void isPasswordValid(String password) {
        if (password == null || password.trim().isEmpty())
            throw new FormatException("La contraseña no puede estar vacia");
        if (password.contains(" "))
            throw new FormatException("La contraseña no puede contener espacios en blanco");

        if (!(password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[0-9].*") &&
                password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~].*"))) {
            throw new FormatException("La contraseña tiene que contener una mayúscula, un número y un símbolo");
        }
    }
}
