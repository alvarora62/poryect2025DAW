package com.daw.quickShip.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El nombre de usuario es obligatorio")
    private String username;

    @NotEmpty(message = "La accion es obligatoria")
    private String accion;

    private LocalDate fecha;

    @PrePersist
    public void prePersist() {
        this.fecha = LocalDate.now();
    }
}
