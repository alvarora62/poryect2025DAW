package com.daw.quickShip.utils;

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
     * @return true si la contraseña cumple con todos los criterios de validación, false en caso contrario.
     */
    public static boolean isPasswordValid(String password) {
        if (password == null || password.trim().isEmpty())
            return false;
        if (password.contains(" "))
            return false;

        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[0-9].*") &&
                password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~].*");
    }
}
