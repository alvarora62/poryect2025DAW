package com.daw.quickShip.servicies;

import com.daw.quickShip.DTOs.RegisterDTO;
import com.daw.quickShip.entities.Credenciales;

public interface CredencialesService {
    Credenciales registrar(RegisterDTO registerDTO);
    Credenciales login(String username, String password);
}
