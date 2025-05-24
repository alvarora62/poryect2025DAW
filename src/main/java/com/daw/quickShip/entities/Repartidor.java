package com.daw.quickShip.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Repartidor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El nombre no puede estar vacío")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+$", message = "El nombre solo debe contener letras y espacios")
    private String nombre;

    @NotEmpty(message = "El DNI no puede estar vacío")
    @Pattern(regexp = "^[0-9]{8}[A-Z]+$", message = "El dni debe tener un formato especifico")
    private String dni;

    @NotEmpty(message = "El teléfono no puede estar vacío")
    @Pattern(regexp = "^[0-9]{9}$", message = "El teléfono debe tener 9 dígitos")
    private String telefono;

    @NotEmpty(message = "El email no puede estar vacío")
    @Email(message = "Formato de email inválido")
    private String email;

    private boolean active = true;

    @NotEmpty(message = "El nombre de la empresa no puede estar vacío")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+$", message = "El nombre de la empresa solo debe contener letras y espacios")
    private String nombreEmpresa;
}
