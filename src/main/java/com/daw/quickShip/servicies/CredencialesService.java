package com.daw.quickShip.servicies;

import com.daw.quickShip.DTOs.RegisterCredencialesDTO;
import com.daw.quickShip.entities.Credenciales;

public interface CredencialesService {
    Credenciales registrar(RegisterCredencialesDTO registerCredencialesDTO);
    Credenciales login(String username, String password);
}
