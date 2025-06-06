package com.daw.quickShip.repositories;

import com.daw.quickShip.entities.Repartidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepartidorRepository extends JpaRepository<Repartidor, Long> {

    @Query("SELECT DISTINCT r.nombreEmpresa FROM Repartidor r WHERE r.nombreEmpresa IS NOT NULL AND r.active = true")
    List<String> findDistinctNombreEmpresa();
    List<Repartidor> findByNombreEmpresa(String nombreEmpresa);
    Optional<Repartidor> findByDni(String dni);

    boolean existsByDni(String dni);
    boolean existsByEmail(String email);


}
