package com.daw.quickShip.servicies;

import com.daw.quickShip.DTOs.RegisterRepartidorDTO;
import com.daw.quickShip.DTOs.SelectRepartidorDTO;
import com.daw.quickShip.entities.Repartidor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RepartidorService {

    Page<Repartidor> listAll(Pageable pageable);
    List<String> selectList();
    List<Repartidor> findByNombreEmpresa(String nombreEmpresa);

    ResponseEntity<Void> create(RegisterRepartidorDTO registerRepartidorDTO);
    ResponseEntity<Void> update(String dni, RegisterRepartidorDTO registerRepartidorDTO);
    ResponseEntity<Void> changeActiveStatus(String dniRepartidor, boolean isActive);
}
