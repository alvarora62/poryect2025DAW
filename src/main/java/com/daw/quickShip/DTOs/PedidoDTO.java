package com.daw.quickShip.DTOs;

public record PedidoDTO(

        int id,

        String direccion,

        String estado,

        Long idProducto,

        int cantidad,

        Long idEmpleado,

        String nombreEmpresa

        ) {
}
