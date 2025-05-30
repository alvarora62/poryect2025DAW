package com.daw.quickShip.servicies;

import com.daw.quickShip.entities.EstadoPedido;

public interface EstadoPedidoService {

    EstadoPedido findByName(String status);
}
