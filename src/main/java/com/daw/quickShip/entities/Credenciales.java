package com.daw.quickShip.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "credenciales")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Credenciales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El nombre de usuario no puede estar vacío")
    private String username;

    @NotEmpty(message = "La contraseña no puede estar vacía")
    private String password;

    @OneToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    @OneToOne
    @JoinColumn(name = "repartidor_id")
    private Repartidor repartidor;
}
