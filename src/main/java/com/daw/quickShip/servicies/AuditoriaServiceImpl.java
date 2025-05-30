package com.daw.quickShip.servicies;

import com.daw.quickShip.entities.Auditoria;
import com.daw.quickShip.repositories.AuditoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuditoriaServiceImpl implements AuditoriaService{

    private final AuditoriaRepository auditoriaRepository;

    /**
     * Retrieves a paginated list of audit entries.
     *
     * @param pageable the pagination information.
     * @return a {@link Page} of {@link Auditoria} records.
     */
    @Override
    public Page<Auditoria> verHistorial(Pageable pageable) {
        return auditoriaRepository.findAll(pageable);
    }

    /**
     * Records an audit entry with the specified username and action.
     *
     * @param username the name of the user performing the action.
     * @param accion the action performed by the user.
     * @return {@link ResponseEntity#ok()} if successful, or {@link ResponseEntity#badRequest()} if an error occurs.
     */
    @Override
    @Transactional
    public ResponseEntity<Void> realizarAnotacion(String username, String accion) {
        try {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("Username cannot be null or blank");
            }
            if (accion == null || accion.trim().isEmpty()) {
                throw new IllegalArgumentException("Accion cannot be null or blank");
            }

            // Create the Auditoria object
            Auditoria auditoria = Auditoria.builder()
                    .username(username)
                    .accion(accion)
                    .build();

            auditoriaRepository.save(auditoria);

        } catch (IllegalArgumentException e) {
            System.err.println("Error en los parámetros proporcionados: {" + e.getMessage() + "}");
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            System.err.println("Error al realizar la anotación. " + e);
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
