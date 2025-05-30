package com.daw.quickShip.servicies;

import com.daw.quickShip.DTOs.RegisterCredencialesDTO;
import com.daw.quickShip.entities.Credenciales;
import com.daw.quickShip.repositories.CredencialesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CredencialesServiceImpl implements CredencialesService {

    private final CredencialesRepository credencialesRepository;
    private final PasswordEncoder encoder;

    @Override
    public Credenciales registrar(RegisterCredencialesDTO registerCredencialesDTO) {


        Credenciales credenciales = Credenciales.builder()
                .username(registerCredencialesDTO.username())
                .password(encoder.encode(registerCredencialesDTO.password()))
                .empleado(registerCredencialesDTO.empleado())
                .repartidor(registerCredencialesDTO.repartidor())
                .build();
        return credencialesRepository.save(credenciales);
    }

    @Override
    public Credenciales login(String username, String rawPassword) {
        return credencialesRepository.findByUsername(username)
                .filter(c -> encoder.matches(rawPassword, c.getPassword()))
                .orElse(null);
    }
}
