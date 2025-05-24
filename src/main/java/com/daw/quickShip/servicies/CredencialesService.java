package com.daw.quickShip.servicies;

import com.daw.quickShip.entities.Credenciales;

public interface CredencialesService {
    Credenciales registrar(Credenciales credenciales);
    Credenciales login(String username, String password);
}
