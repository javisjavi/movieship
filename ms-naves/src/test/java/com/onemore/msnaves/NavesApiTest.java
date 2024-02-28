package com.onemore.msnaves;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.onemore.msnaves.common.PaginatedRequest;
import com.onemore.msnaves.naves.controller.NavesApi;
import com.onemore.msnaves.naves.db.Nave;
import com.onemore.msnaves.naves.db.NaveDto;
import com.onemore.msnaves.naves.service.NavesService;

class NavesApiTest {

    @Mock
    private NavesService navesService;

    @InjectMocks
    private NavesApi navesApi;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllNaves() {
        // Arrange
        List<Nave> naves = new ArrayList<>();
        naves.add(new Nave());
        when(navesService.getAll()).thenReturn(naves);

        // Act
        ResponseEntity<List<Nave>> responseEntity = navesApi.getAllNaves();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(naves, responseEntity.getBody());
    }

    @Test
    void testGetAllNavesWithPagination() {
        // Arrange
        List<Nave> naves = new ArrayList<>();
        naves.add(new Nave());
        PaginatedRequest paginationRequest = new PaginatedRequest(0, 10);
        when(navesService.getAllPaginated(any())).thenReturn(naves);

        // Act
        ResponseEntity<List<Nave>> responseEntity = navesApi.getAllNaves(paginationRequest);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(naves, responseEntity.getBody());
    }

    @Test
    void testGetNaveById() {
        // Arrange
        Nave nave = new Nave();
        Integer id = 1;
        when(navesService.getById(id)).thenReturn(Optional.of(nave));

        // Act
        ResponseEntity<Nave> responseEntity = navesApi.getNaveById(id);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(nave, responseEntity.getBody());
    }

    @Test
    void testGetNaveByIdNotFound() {
        // Arrange
        Integer id = 1;
        when(navesService.getById(id)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Nave> responseEntity = navesApi.getNaveById(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void testCreateNave() {
        // Arrange
        NaveDto naveDto = new NaveDto();
        Nave nave = new Nave();
        when(navesService.create(any())).thenReturn(nave);

        // Act
        ResponseEntity<Nave> responseEntity = navesApi.createNave(naveDto);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(nave, responseEntity.getBody());
    }

    @Test
    void testUpdateNave() {
        // Arrange
        Integer id = 1;
        NaveDto naveDto = new NaveDto();
        Nave nave = new Nave();
        when(navesService.update(eq(id), any())).thenReturn(nave);

        // Act
        ResponseEntity<Nave> responseEntity = navesApi.updateNave(id, naveDto);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(nave, responseEntity.getBody());
    }

    @Test
    void testDeleteNave() {
        // Arrange
        Integer id = 1;

        // Act
        ResponseEntity<Void> responseEntity = navesApi.deleteNave(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(navesService, times(1)).delete(id);
    }

    @Test
    void testGetNaveByNombre() {
        // Arrange
        String nombre = "Falcon";
        List<Nave> naves = new ArrayList<>();
        naves.add(new Nave());
        when(navesService.findByPartialNombre(nombre)).thenReturn(naves);

        // Act
        ResponseEntity<List<Nave>> responseEntity = navesApi.getNaveByNombre(nombre);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(naves, responseEntity.getBody());
    }

    @Test
    void testGetNaveByNombreNotFound() {
        // Arrange
        String nombre = "x-wing";
        when(navesService.findByPartialNombre(nombre)).thenReturn(new ArrayList<>());

        // Act
        ResponseEntity<List<Nave>> responseEntity = navesApi.getNaveByNombre(nombre);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void testTest() {
        // Act
        HttpStatus status = navesApi.test();

        // Assert
        assertEquals(HttpStatus.OK, status);
    }

    @Test
    void testLogin() {
        // Act
        ResponseEntity<String> responseEntity = navesApi.login();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("EsTo.Es.Un.ToKeN", responseEntity.getBody());
    }
    
    /*
     * Los internal server error
     */
    
    @Test
    void testGetAllNavesInternalServerError() {
        // Arrange
        when(navesService.getAll()).thenThrow(new RuntimeException("Internal Server Error"));

        // Act
        ResponseEntity<List<Nave>> responseEntity = navesApi.getAllNaves();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void testGetAllNavesWithPaginationInternalServerError() {
        // Arrange
        PaginatedRequest paginationRequest = new PaginatedRequest(0, 10);
        when(navesService.getAllPaginated(any())).thenThrow(new RuntimeException("Internal Server Error"));

        // Act
        ResponseEntity<List<Nave>> responseEntity = navesApi.getAllNaves(paginationRequest);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void testGetNaveByIdInternalServerError() {
        // Arrange
        Integer id = 1;
        when(navesService.getById(id)).thenThrow(new RuntimeException("Internal Server Error"));

        // Act
        ResponseEntity<Nave> responseEntity = navesApi.getNaveById(id);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void testCreateNaveInternalServerError() {
        // Arrange
        NaveDto naveDto = new NaveDto();
        when(navesService.create(any())).thenThrow(new RuntimeException("Internal Server Error"));

        // Act
        ResponseEntity<Nave> responseEntity = navesApi.createNave(naveDto);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void testUpdateNaveInternalServerError() {
        // Arrange
        Integer id = 1;
        NaveDto naveDto = new NaveDto();
        when(navesService.update(eq(id), any())).thenThrow(new RuntimeException("Internal Server Error"));

        // Act
        ResponseEntity<Nave> responseEntity = navesApi.updateNave(id, naveDto);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
    
    @Test
    void testDeleteNaveInternalServerError() {
        // Arrange
        Integer id = 1;
        doThrow(new RuntimeException("Internal Server Error")).when(navesService).delete(id);

        // Act
        ResponseEntity<Void> responseEntity = navesApi.deleteNave(id);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(navesService, times(1)).delete(id);
    }
    
    @Test
    void testGetNaveByNombreInternalServerError() {
        // Arrange
        String nombre = "x-wing";
        when(navesService.findByPartialNombre(nombre)).thenThrow(new RuntimeException("Internal Server Error"));

        // Act
        ResponseEntity<List<Nave>> responseEntity = navesApi.getNaveByNombre(nombre);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
    
}

