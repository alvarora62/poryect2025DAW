package com.daw.quickShip.servicies;

import com.daw.quickShip.DTOs.RegisterCredencialesDTO;
import com.daw.quickShip.entities.Credenciales;
import com.daw.quickShip.repositories.CredencialesRepository;
import com.daw.quickShip.utils.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CredencialesServiceImpl implements CredencialesService {

    private final CredencialesRepository credencialesRepository;
    private final PasswordEncoder encoder;

    /**
     * Registers new user credentials after validating the password.
     *
     * @param registerCredencialesDTO the DTO containing user registration information.
     * @return the saved {@link Credenciales} entity.
     * @throws IllegalArgumentException if the password does not meet security requirements.
     */
    @Override
    @Transactional
    public Credenciales registrar(RegisterCredencialesDTO registerCredencialesDTO) {
        PasswordValidator.isPasswordValid(registerCredencialesDTO.password());

        Credenciales credenciales = Credenciales.builder()
                .username(registerCredencialesDTO.username())
                .password(encoder.encode(registerCredencialesDTO.password()))
                .empleado(registerCredencialesDTO.empleado())
                .repartidor(registerCredencialesDTO.repartidor())
                .build();
        return credencialesRepository.save(credenciales);
    }

    /**
     * Authenticates a user based on the provided username and raw password.
     *
     * @param username the username to authenticate.
     * @param rawPassword the raw (unencrypted) password to validate.
     * @return the matching {@link Credenciales} if credentials are valid, or {@code null} if not.
     */
    @Override
    public Credenciales login(String username, String rawPassword) {
        return credencialesRepository.findByUsername(username)
                .filter(c -> encoder.matches(rawPassword, c.getPassword()))
                .orElse(null);
    }
}
