package com.daw.quickShip.servicies;

import com.daw.quickShip.DTOs.RegisterEmpleadoDTO;
import com.daw.quickShip.DTOs.SelectEmpleadoDTO;
import com.daw.quickShip.entities.Empleado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmpleadoService {

    Page<Empleado> listAll(Pageable pageable);
    List<SelectEmpleadoDTO> selectList();
    Empleado findById(Long idEmpleado);

    ResponseEntity<Void> create(RegisterEmpleadoDTO dto);
    ResponseEntity<Void> update(String dni, RegisterEmpleadoDTO dto);
    ResponseEntity<Void> changeActiveStatus(String idEmpleado, boolean isActive);
}
