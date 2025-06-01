package com.daw.quickShip.servicies;

import com.daw.quickShip.entities.EstadoPedido;
import com.daw.quickShip.exceptions.ResourceNotFoundException;

public interface EstadoPedidoService {

    /**
     * Finds an order status by its name.
     *
     * @param status the name of the status to find.
     * @return the {@link EstadoPedido} matching the given name.
     * @throws ResourceNotFoundException if no status with the given name exists.
     */
    EstadoPedido findByName(String status);
}
