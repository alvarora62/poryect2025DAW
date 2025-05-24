package com.daw.quickShip.servicies;

import com.daw.quickShip.entities.Credenciales;
import com.daw.quickShip.repositories.CredencialesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CredencialesServiceImpl implements CredencialesService {

    private final CredencialesRepository credencialesRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Credenciales registrar(Credenciales credenciales) {
        credenciales.setPassword(passwordEncoder.encode(credenciales.getPassword()));
        return credencialesRepository.save(credenciales);
    }

    @Override
    public Credenciales login(String username, String rawPassword) {
        return credencialesRepository.findByUsername(username)
                .filter(c -> passwordEncoder.matches(rawPassword, c.getPassword()))
                .orElse(null);
    }
}
