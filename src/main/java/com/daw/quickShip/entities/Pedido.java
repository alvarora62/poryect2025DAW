package com.daw.quickShip.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String direccion;

    @ManyToOne
    @JoinColumn(name = "repartidor_id")
    private Repartidor repartidor;

    private LocalDate fechaCreacion;
    private LocalDate fechaEntrega;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDate.now();
    }
}
