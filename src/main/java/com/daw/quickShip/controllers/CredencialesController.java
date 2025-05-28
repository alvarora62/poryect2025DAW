package com.daw.quickShip.controllers;

import com.daw.quickShip.DTOs.LoginDTO;
import com.daw.quickShip.DTOs.RegisterCredencialesDTO;
import com.daw.quickShip.entities.Credenciales;
import com.daw.quickShip.servicies.CredencialesServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Autenticación", description = "API para registrar e iniciar sesión con credenciales")
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CredencialesController {

    private final CredencialesServiceImpl credencialesService;

    @PostMapping("/signup")
    @Operation(summary = "Registrar nuevo usuario", description = "Crea una nueva cuenta de usuario con nombre de usuario y contraseña")
    public ResponseEntity<?> registrar(@Valid @RequestBody RegisterCredencialesDTO registerCredencialesDTO) {
        Credenciales nuevo = credencialesService.registrar(registerCredencialesDTO);
        return ResponseEntity.ok(nuevo);
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica al usuario utilizando nombre de usuario y contraseña")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        Credenciales user = credencialesService.login(loginDTO.username(), loginDTO.password());
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }
    }
}