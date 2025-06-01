package com.daw.quickShip.servicies;

import com.daw.quickShip.entities.EstadoPedido;
import com.daw.quickShip.exceptions.ResourceNotFoundException;
import com.daw.quickShip.repositories.EstadoPedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EstadoPedidoServiceImpl implements EstadoPedidoService{

    private final EstadoPedidoRepository estadoPedidoRepository;

    @Override
    public EstadoPedido findByName(String status) {
        return estadoPedidoRepository.findByNombre(status.toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("Estado not valid."));
    }
}
