package com.daw.quickShip.servicies;

import com.daw.quickShip.DTOs.PedidoDTO;
import com.daw.quickShip.entities.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface PedidoService {

    Page<Pedido> listAll(Pageable pageable);
    Pedido findById(Long idPedido);

    ResponseEntity<Void> save(PedidoDTO pedidoDTO);

    ResponseEntity<Void> setEmpleado(Long ididPedido, Long idEmpleado);
    ResponseEntity<Void> setRepartidor(Long idPedido, String nombreEmpresa);
    ResponseEntity<Void> changeActiveStatus(Long idPedido, String status);
}
