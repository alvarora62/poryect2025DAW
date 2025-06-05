package com.daw.quickShip.servicies;

import com.daw.quickShip.entities.EstadoPedido;
import com.daw.quickShip.exceptions.ResourceNotFoundException;
import com.daw.quickShip.repositories.EstadoPedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstadoPedidoServiceImpl implements EstadoPedidoService{

    private final EstadoPedidoRepository estadoPedidoRepository;

    @Override
    public List<EstadoPedido> findAll() {
        return estadoPedidoRepository.findAll();
    }

    @Override
    public EstadoPedido findByName(String status) {
        return estadoPedidoRepository.findByNombre(status.toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("Estado not valid."));
    }

    @Override
    public void save(EstadoPedido estadoPedido) {
        estadoPedidoRepository.save(estadoPedido);
    }
}
