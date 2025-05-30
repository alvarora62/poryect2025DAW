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
    private final EmpleadoService empleadoService;
    private final RepartidorService repartidorService;
    private final EstadoPedidoService estadoPedidoService;
    private final ProductoService productoService;

    /**
     * Retrieves a paginated list of all orders.
     *
     * @param pageable the pagination and sorting information.
     * @return a page of {@link Pedido} entities.
     */
    @Override
    public Page<Pedido> listAll(Pageable pageable) {
        return pedidoRepository.findAll(pageable);
    }

    /**
     * Finds an order by its ID.
     *
     * @param idPedido the ID of the order to retrieve.
     * @return the {@link Pedido} found.
     * @throws ResourceNotFoundException if no order is found with the given ID.
     */
    @Override
    public Pedido findById(Long idPedido) {
        return pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido not found."));
    }

    /**
     * Creates and saves a new {@link Pedido} using data from a {@link PedidoDTO}.
     * <p>
     * The new order is initialized with a default state of "NOT STARTED", and it is
     * associated with a specific product and employee. The delivery company is set
     * as an empty string by default.
     * </p>
     *
     * @param pedidoDTO the data transfer object containing order details.
     * @return a {@link ResponseEntity} indicating success with HTTP 200 OK.
     * @throws ResourceNotFoundException if the product or employee is not found,
     *                                   or if the default state is missing.
     */
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

        pedidoRepository.save(pedido);
        return ResponseEntity.ok().build();
    }


    /**
     * Assigns an employee to a specific order.
     *
     * @param idPedido   the ID of the order.
     * @param idEmpleado the ID of the employee to assign.
     * @return a {@link ResponseEntity} indicating success.
     */
    @Override
    @Transactional
    public ResponseEntity<Void> setEmpleado(Long idPedido, Long idEmpleado) {
        Pedido pedido = findById(idPedido);
        Empleado empleado = empleadoService.findById(idEmpleado);

        pedido.setEmpleado(empleado);
        pedidoRepository.save(pedido);
        return ResponseEntity.ok().build();
    }

    /**
     * Assigns a delivery person (repartidor) to an order from the specified company.
     * Selects the repartidor with the fewest orders assigned.
     *
     * @param idPedido       the ID of the order.
     * @param nombreEmpresa  the name of the delivery company.
     * @return a {@link ResponseEntity} indicating success.
     * @throws ResourceNotFoundException if no repartidor is found.
     */
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
        pedidoRepository.save(pedido);

        return ResponseEntity.ok().build();
    }

    /**
     * Updates the status of a given order.
     *
     * @param idPedido the ID of the order to update.
     * @param status   the new status to apply.
     * @return a {@link ResponseEntity} indicating success.
     * @throws ResourceNotFoundException if the order or status is not found.
     */
    @Override
    @Transactional
    public ResponseEntity<Void> changeActiveStatus(Long idPedido, String status) {
        Pedido pedido = findById(idPedido);
        EstadoPedido estadoPedido = estadoPedidoService.findByName(status);

        pedido.setEstado(estadoPedido);
        pedidoRepository.save(pedido);
        return ResponseEntity.ok().build();
    }
}
