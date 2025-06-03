package com.daw.quickShip.service;

import com.daw.quickShip.entities.Auditoria;
import com.daw.quickShip.repositories.AuditoriaRepository;
import com.daw.quickShip.servicies.AuditoriaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AuditoriaServiceImplTest {

    @Mock
    private AuditoriaRepository auditoriaRepository;

    @InjectMocks
    private AuditoriaServiceImpl auditoriaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void verHistorial_ShouldReturnPageOfAuditorias() {
        PageRequest pageable = PageRequest.of(0, 10);
        Auditoria auditoria = Auditoria.builder()
                .username("testUser")
                .accion("testAction")
                .build();
        Page<Auditoria> expectedPage = new PageImpl<>(Collections.singletonList(auditoria));

        when(auditoriaRepository.findAll(pageable)).thenReturn(expectedPage);

        Page<Auditoria> result = auditoriaService.verHistorial(pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().getFirst().getUsername()).isEqualTo("testUser");
        verify(auditoriaRepository, times(1)).findAll(pageable);
    }

    @Test
    void realizarAnotacion_ShouldSaveAuditoriaAndReturnOk() {
        String username = "user1";
        String accion = "logged in";

        ResponseEntity<Void> response = auditoriaService.realizarAnotacion(username, accion);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(auditoriaRepository, times(1)).save(any(Auditoria.class));
    }

    @Test
    void realizarAnotacion_ShouldReturnBadRequestIfUsernameBlank() {
        ResponseEntity<Void> response = auditoriaService.realizarAnotacion("  ", "accion");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        verify(auditoriaRepository, never()).save(any());
    }

    @Test
    void realizarAnotacion_ShouldReturnBadRequestIfAccionBlank() {
        ResponseEntity<Void> response = auditoriaService.realizarAnotacion("user", " ");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        verify(auditoriaRepository, never()).save(any());
    }
}
