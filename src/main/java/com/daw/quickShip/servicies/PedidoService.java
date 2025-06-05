package com.daw.quickShip.servicies;

import com.daw.quickShip.DTOs.PedidoDTO;
import com.daw.quickShip.entities.Pedido;
import com.daw.quickShip.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface PedidoService {

    /**
     * Retrieves a paginated list of all orders.
     *
     * @param pageable the pagination and sorting information.
     * @return a page of {@link Pedido} entities.
     */
    Page<Pedido> listAll(Pageable pageable);

    /**
     * Finds an order by its ID.
     *
     * @param idPedido the ID of the order to retrieve.
     * @return the {@link Pedido} found.
     * @throws ResourceNotFoundException if no order is found with the given ID.
     */
    Pedido findById(Long idPedido);

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
    ResponseEntity<Void> save(PedidoDTO pedidoDTO);

    /**
     * Assigns an employee to a specific order.
     *
     * @param ididPedido   the ID of the order.
     * @param idEmpleado the ID of the employee to assign.
     * @return a {@link ResponseEntity} indicating success.
     */
    ResponseEntity<Void> setEmpleado(Long ididPedido, Long idEmpleado);

    /**
     * Assigns a delivery person (repartidor) to an order from the specified company.
     * Selects the repartidor with the fewest orders assigned.
     *
     * @param idPedido       the ID of the order.
     * @param nombreEmpresa  the name of the delivery company.
     * @return a {@link ResponseEntity} indicating success.
     * @throws ResourceNotFoundException if no repartidor is found.
     */
    ResponseEntity<Void> setRepartidor(Long idPedido, String nombreEmpresa);

    /**
     * Updates the status of a given order.
     *
     * @param idPedido the ID of the order to update.
     * @param status   the new status to apply.
     * @return a {@link ResponseEntity} indicating success.
     * @throws ResourceNotFoundException if the order or status is not found.
     */
    ResponseEntity<Void> changeActiveStatus(Long idPedido, String status);
}
