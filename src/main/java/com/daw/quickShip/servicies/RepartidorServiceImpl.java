package com.daw.quickShip.servicies;

import com.daw.quickShip.entities.Repartidor;
import com.daw.quickShip.repositories.RepartidorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class RepartidorServiceImpl implements RepartidorService {

    @Autowired
    private RepartidorRepository repartidorRepository;

    @Override
    public Page<Repartidor> listAll(Pageable pageable) {
        return repartidorRepository.findAll(pageable);
    }

    @Override
    public Page<Repartidor> listAllActive(Pageable pageable) {
        return repartidorRepository.findByIsActive(true, pageable);
    }

    @Override
    public Page<Repartidor> listAllNotActive(Pageable pageable) {
        return repartidorRepository.findByIsActive(false, pageable);
    }

    @Override
    public ResponseEntity<Void> save(Repartidor repartidor) {
        repartidorRepository.save(repartidor);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> changeActiveStatus(Repartidor repartidor, boolean isActive) {
        Optional<Repartidor> existingRepartidor = repartidorRepository.findById(repartidor.getId());
        if (existingRepartidor.isPresent()) {
            Repartidor updatedRepartidor = existingRepartidor.get();
            updatedRepartidor.setActive(isActive);
            repartidorRepository.save(updatedRepartidor);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}