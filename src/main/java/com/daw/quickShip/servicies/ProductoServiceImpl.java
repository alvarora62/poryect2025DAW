package com.daw.quickShip.servicies;

import com.daw.quickShip.entities.Producto;
import com.daw.quickShip.exceptions.ResourceNotFoundException;
import com.daw.quickShip.repositories.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService{

    private final ProductoRepository productoRepository;

    @Override
    public Producto findById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto not found."));
    }

    @Override
    public void save(Producto producto) {
        productoRepository.save(producto);
    }
}
