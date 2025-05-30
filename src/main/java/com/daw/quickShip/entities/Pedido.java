package com.daw.quickShip.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@Entity
@Table(name = "Pedido")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String direccion;

    @ManyToOne
    @JoinColumn(name = "estado_id")
    private EstadoPedido estado;

    @ManyToOne(optional = true)
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private int cantidad;

    @ManyToOne(optional = true)
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "repartidor_id")
    private Repartidor repartidor;

    private String repartidorEmpresa;

    private LocalDate fechaCreacion;

    private LocalDate fechaEntrega;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDate.now();
        this.fechaEntrega = LocalDate.now().plusDays(7);
    }
}
