package com.daw.quickShip.servicies;

import com.daw.quickShip.entities.Empleado;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService{
    @Override
    public Page<Empleado> listAll(Pageable pageable) {
        return null;
    }
}
