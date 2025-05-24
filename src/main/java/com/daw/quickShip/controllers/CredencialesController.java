package com.daw.quickShip.controllers;

import com.daw.quickShip.entities.Credenciales;
import com.daw.quickShip.servicies.CredencialesService;
import com.daw.quickShip.servicies.CredencialesServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "API para registrar e iniciar sesión con credenciales")
public class CredencialesController {

    private final CredencialesServiceImpl credencialesService;

    @PostMapping("/signup")
    @Operation(
            summary = "Registrar nuevo usuario",
            description = "Crea una nueva cuenta de usuario con nombre de usuario y contraseña"
    )
    public ResponseEntity<?> registrar(@Valid @RequestBody Credenciales credenciales) {
        Credenciales nuevo = credencialesService.registrar(credenciales);
        return ResponseEntity.ok(nuevo);
    }

    @PostMapping("/login")
    @Operation(
            summary = "Iniciar sesión",
            description = "Autentica al usuario utilizando nombre de usuario y contraseña"
    )
    public ResponseEntity<?> login(@RequestBody Credenciales credenciales) {
        Credenciales user = credencialesService.login(credenciales.getUsername(), credenciales.getPassword());
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }
    }
}