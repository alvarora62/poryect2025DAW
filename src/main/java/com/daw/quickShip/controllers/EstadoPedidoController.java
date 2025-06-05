package com.daw.quickShip.controllers;

import com.daw.quickShip.entities.EstadoPedido;
import com.daw.quickShip.servicies.EstadoPedidoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estadoPedido")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class EstadoPedidoController {

    private final EstadoPedidoServiceImpl pedidoService;

    @GetMapping
    public List<EstadoPedido> findAll(){
        return pedidoService.findAll();
    }

    @PostMapping("/save")
    public void save(@RequestBody EstadoPedido estadoPedido) {
        pedidoService.save(estadoPedido);
    }
}
