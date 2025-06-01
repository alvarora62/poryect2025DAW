package com.daw.quickShip.controllers;

import com.daw.quickShip.DTOs.PedidoDTO;
import com.daw.quickShip.entities.Pedido;
import com.daw.quickShip.servicies.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Pedido", description = "API for managing pedidos")
@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping
    @Operation(summary = "List all pedidos", description = "Retrieves a paginated list of all pedidos.")
    public Page<Pedido> listAll(Pageable pageable) {
        return pedidoService.listAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find pedido by ID", description = "Retrieves a specific pedido by its ID.")
    public Pedido findById(@PathVariable Long id) {
        return pedidoService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new pedido", description = "Saves a new pedido using provided data.")
    public ResponseEntity<Void> save(@RequestBody PedidoDTO pedidoDTO) {
        return pedidoService.save(pedidoDTO);
    }

    @PutMapping("/{id}/empleado/{empleadoId}")
    @Operation(summary = "Assign an empleado", description = "Assigns an empleado to a pedido.")
    public ResponseEntity<Void> setEmpleado(@PathVariable("id") Long pedidoId, @PathVariable("empleadoId") Long empleadoId) {
        return pedidoService.setEmpleado(pedidoId, empleadoId);
    }

    @PutMapping("/{id}/repartidor/{nombreEmpresa}")
    @Operation(summary = "Assign a repartidor by empresa", description = "Assigns a repartidor with the fewest deliveries from a given empresa.")
    public ResponseEntity<Void> setRepartidor(@PathVariable("id") Long pedidoId, @PathVariable String nombreEmpresa) {
        return pedidoService.setRepartidor(pedidoId, nombreEmpresa);
    }

    @PutMapping("/{id}/estado")
    @Operation(summary = "Change estado of pedido", description = "Changes the status of a pedido.")
    public ResponseEntity<Void> changeActiveStatus(@PathVariable("id") Long pedidoId, @RequestParam String status) {
        return pedidoService.changeActiveStatus(pedidoId, status);
    }
}
