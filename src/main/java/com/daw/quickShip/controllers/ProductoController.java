package com.daw.quickShip.controllers;

import com.daw.quickShip.entities.Producto;
import com.daw.quickShip.servicies.ProductoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/producto")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoServiceImpl productoService;

    @PostMapping("/save")
    public void save(@RequestBody Producto producto) {
        productoService.save(producto);
    }
}
