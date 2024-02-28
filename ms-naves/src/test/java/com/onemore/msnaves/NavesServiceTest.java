package com.onemore.msnaves;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import com.onemore.msnaves.common.MensajesService;
import com.onemore.msnaves.naves.db.INavesRepository;
import com.onemore.msnaves.naves.db.Nave;
import com.onemore.msnaves.naves.service.NavesService;

class NavesServiceTest {

    private NavesService navesService;
    private INavesRepository navesRepository;
    private MensajesService mensajesService;

    @BeforeEach
    void setUp() {
        navesRepository = mock(INavesRepository.class);
        mensajesService = mock(MensajesService.class);
        navesService = new NavesService(navesRepository, mensajesService);
    }

    @Test
    void testGetAll() {
        // Arrange
        List<Nave> naves = new ArrayList<>();
        naves.add(new Nave());
        when(navesRepository.findAllByOrderByIdAsc()).thenReturn(naves);

        // Act
        List<Nave> result = navesService.getAll();

        // Assert
        assertEquals(naves, result);
    }

    @Test
    void testGetAllPaginated() {
        // Arrange
        List<Nave> naves = new ArrayList<>();
        naves.add(new Nave());
        PageRequest pageRequest = PageRequest.of(0, 10);
        when(navesRepository.findAllPaginated(10, 0)).thenReturn(naves);

        // Act
        List<Nave> result = navesService.getAllPaginated(pageRequest);

        // Assert
        assertEquals(naves, result);
    }

    @Test
    void testGetById() {
        // Arrange
        Integer id = 1;
        Nave nave = new Nave();
        when(navesRepository.findById(id)).thenReturn(Optional.of(nave));

        // Act
        Optional<Nave> result = navesService.getById(id);

        // Assert
        assertEquals(nave, result.get());
    }

    @Test
    void testGetByIdNotFound() {
        // Arrange
        Integer id = 1;
        when(navesRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            Optional<Nave> result = navesService.getById(id);
            if (!result.isPresent()) {
                throw new RuntimeException("Nave not found with ID: " + id);
            }
        });
    }

    @Test
    void testCreate() {
        // Arrange
        Nave nave = new Nave();
        when(navesRepository.save(nave)).thenReturn(nave);

        // Act
        Nave result = navesService.create(nave);

        // Assert
        assertEquals(nave, result);
    }

    @Test
    void testUpdate() {
        // Arrange
        Integer id = 1;
        Nave nave = new Nave();
        Nave existingNave = new Nave(); // Simulamos que existe una nave con el ID proporcionado
        when(navesRepository.findById(id)).thenReturn(Optional.of(existingNave));
        when(navesRepository.save(existingNave)).thenReturn(existingNave); // Simulamos que la actualización fue exitosa

        // Act
        Nave result = navesService.update(id, nave);

        // Assert
        assertEquals(existingNave, result);
    }

    @Test
    void testUpdateNotFound() {
        // Arrange
        Integer id = 1;
        Nave nave = new Nave();
        when(navesRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> navesService.update(id, nave));
    }

    @Test
    void testDelete() {
        // Arrange
        Integer id = 1;
        Nave nave = new Nave();
        when(navesRepository.findById(id)).thenReturn(Optional.of(nave));

        // Act
        navesService.delete(id);

        // Assert
        verify(navesRepository).delete(nave);
    }

    @Test
    void testDeleteNotFound() {
        // Arrange
        Integer id = 1;
        when(navesRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> navesService.delete(id));
    }

    @Test
    void testFindByNombre() {
        // Arrange
        String nombre = "x-wing";
        List<Nave> naves = new ArrayList<>();
        naves.add(new Nave());
        when(navesRepository.findByNombre(nombre)).thenReturn(naves);

        // Act
        List<Nave> result = navesService.findByNombre(nombre);

        // Assert
        assertEquals(naves, result);
    }

    @Test
    void testFindByPartialNombre() {
        // Arrange
        String nombre = "x-wing";
        List<Nave> naves = new ArrayList<>();
        naves.add(new Nave());
        when(navesRepository.findByPartialNombre(nombre)).thenReturn(naves);

        // Act
        List<Nave> result = navesService.findByPartialNombre(nombre);

        // Assert
        assertEquals(naves, result);
    }

    @Test
    void testMensajesServiceCalledOnGetAll() {
        // Arrange
        List<Nave> naves = new ArrayList<>();
        naves.add(new Nave());
        when(navesRepository.findAllByOrderByIdAsc()).thenReturn(naves);

        // Act
        navesService.getAll();

        // Assert
        verify(mensajesService).send("[" + LocalDate.now() + "] - " + "Se ha llamado a getAll");
    }

}
