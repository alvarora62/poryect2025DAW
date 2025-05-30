package com.daw.quickShip.repositories;

import com.daw.quickShip.entities.Pedido;
import com.daw.quickShip.entities.Repartidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByRepartidor_NombreEmpresa(String nombreEmpresa);
    int countByRepartidor(Repartidor repartidor);
}
