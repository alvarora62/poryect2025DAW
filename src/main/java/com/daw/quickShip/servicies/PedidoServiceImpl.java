package com.daw.quickShip.servicies;

import com.daw.quickShip.DTOs.PedidoDTO;
import com.daw.quickShip.entities.Empleado;
import com.daw.quickShip.entities.EstadoPedido;
import com.daw.quickShip.entities.Pedido;
import com.daw.quickShip.entities.Repartidor;
import com.daw.quickShip.exceptions.ResourceNotFoundException;
import com.daw.quickShip.repositories.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService{

    private final PedidoRepository pedidoRepository;
    private final EmpleadoServiceImpl empleadoService;
    private final RepartidorServiceImpl repartidorService;
    private final EstadoPedidoServiceImpl estadoPedidoService;
    private final ProductoServiceImpl productoService;
    private final AuditoriaServiceImpl auditoriaService;

    @Override
    public Page<Pedido> listAll(Pageable pageable) {
        return pedidoRepository.findAll(pageable);
    }

    @Override
    public Pedido findById(Long idPedido) {
        return pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido not found."));
    }

    @Override
    @Transactional
    public ResponseEntity<Void> save(PedidoDTO pedidoDTO) {
        Pedido pedido = Pedido.builder()
                .direccion(pedidoDTO.direccion())
                .estado(estadoPedidoService.findByName("NOT STARTED"))
                .producto(productoService.findById(pedidoDTO.idProducto()))
                .cantidad(pedidoDTO.cantidad())
                .empleado(empleadoService.findById(pedidoDTO.idEmpleado()))
                .repartidorEmpresa("")
                .build();

        pedido.setEstado(updateStatus(pedido).getEstado());
        Pedido newPedido = pedidoRepository.save(pedido);
        auditoriaService.realizarAnotacion("admin", "Creado pedido con ID: " + newPedido.getId());
        return ResponseEntity.ok().build();
    }

    @Override
    @Transactional
    public ResponseEntity<Void> setEmpleado(Long idPedido, Long idEmpleado) {
        Pedido pedido = findById(idPedido);
        Empleado empleado = empleadoService.findById(idEmpleado);

        pedido.setEmpleado(empleado);
        pedido.setEstado(updateStatus(pedido).getEstado());
        Pedido newPedido = pedidoRepository.save(pedido);
        auditoriaService.realizarAnotacion("admin", "Asignado empleado con ID: " + idEmpleado + " al pedido con ID: " + newPedido.getId());
        return ResponseEntity.ok().build();
    }

    @Override
    @Transactional
    public ResponseEntity<Void> setRepartidor(Long idPedido, String nombreEmpresa) {
        Pedido pedido = findById(idPedido);

        List<Repartidor> repartidores = repartidorService.findByNombreEmpresa(nombreEmpresa);
        if (repartidores.isEmpty()) {
            throw new ResourceNotFoundException("Repartidores not found for: " + nombreEmpresa);
        }

        Repartidor repartidorConMenosPedidos = repartidores.stream()
                .min(Comparator.comparingInt(pedidoRepository::countByRepartidor))
                .orElseThrow(() -> new ResourceNotFoundException("Any repartidores found to assign."));

        pedido.setRepartidor(repartidorConMenosPedidos);
        pedido.setEstado(updateStatus(pedido).getEstado());
        Pedido newPedido = pedidoRepository.save(pedido);
        auditoriaService.realizarAnotacion("admin", "Asignada empresa: " + nombreEmpresa + " al pedido con ID: " + newPedido.getId());

        return ResponseEntity.ok().build();
    }

    @Override
    @Transactional
    public ResponseEntity<Void> changeActiveStatus(Long idPedido, String status) {
        Pedido pedido = findById(idPedido);
        EstadoPedido estadoPedido = estadoPedidoService.findByName(status);

        pedido.setEstado(estadoPedido);

        Pedido newPedido = pedidoRepository.save(pedido);
        auditoriaService.realizarAnotacion("admin", "Cambiado el estado del pedido con ID: " + newPedido.getId() + " a " + status);
        return ResponseEntity.ok().build();
    }

    /**
     * Updates the status of a {@link Pedido} based on its assigned repartidor and empleado.
     * <ul>
     *     <li>If both repartidor and empleado are null, sets status to "NOT STARTED".</li>
     *     <li>If empleado is null, sets status to "EMPLOYEE NOT ASSIGNED".</li>
     *     <li>If repartidor is null, sets status to "SHIPPING COMPANY NOT ASSIGNED".</li>
     *     <li>Otherwise, sets status to "READY FOR DELIVERY".</li>
     * </ul>
     *
     * @param pedido the {@link Pedido} whose status will be updated.
     * @return the updated {@link Pedido} instance.
     * @throws IllegalStateException if the corresponding status cannot be found.
     */
    private Pedido updateStatus(Pedido pedido) {
        String estado;

        if (pedido.getRepartidor() == null && pedido.getEmpleado() == null) {
            estado = "NOT STARTED";
        } else if (pedido.getEmpleado() == null) {
            estado = "EMPLOYEE NOT ASSIGNED";
        } else if (pedido.getRepartidor() == null) {
            estado = "SHIPPING COMPANY NOT ASSIGNED";
        } else {
            estado = "READY FOR DELIVEY";
        }
        pedido.setEstado(estadoPedidoService.findByName(estado));
        return pedido;
    }

}
