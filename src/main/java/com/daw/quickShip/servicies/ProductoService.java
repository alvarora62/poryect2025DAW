package com.daw.quickShip.servicies;

import com.daw.quickShip.entities.Producto;
import com.daw.quickShip.exceptions.ResourceNotFoundException;

public interface ProductoService {

    /**
     * Finds a {@link Producto} by its ID.
     *
     * @param id the ID of the product to find.
     * @return the {@link Producto} with the specified ID.
     * @throws ResourceNotFoundException if no product with the given ID is found.
     */
    Producto findById(Long id);

    void save(Producto producto);
}
