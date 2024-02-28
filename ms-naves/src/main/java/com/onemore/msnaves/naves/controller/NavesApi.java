package com.onemore.msnaves.naves.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onemore.msnaves.common.PaginatedRequest;
import com.onemore.msnaves.naves.db.Nave;
import com.onemore.msnaves.naves.db.NaveDto;
import com.onemore.msnaves.naves.db.NavesMapper;
import com.onemore.msnaves.naves.service.NavesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/naves")
@Api(value = "Naves Management System", tags = "Naves")
public class NavesApi {

    @Autowired
    private NavesService navesService;

    @ApiOperation(value = "Obtener todas las naves", response = ResponseEntity.class)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Nave>> getAllNaves() {
        try {
            List<Nave> naves = navesService.getAll();
            return new ResponseEntity<>(naves, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Obtener todas las naves paginadas", response = ResponseEntity.class)
    @PostMapping(value = "/paginated", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Nave>> getAllNaves(@RequestBody PaginatedRequest paginatedRequest) {
        try {
            PageRequest pageRequest = PageRequest.of(paginatedRequest.getPageNumber(), paginatedRequest.getPageSize());
            List<Nave> navesPaginated = navesService.getAllPaginated(pageRequest);
            return new ResponseEntity<>(navesPaginated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Obtener una nave por su ID", response = ResponseEntity.class)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Nave> getNaveById(@PathVariable("id") Integer id) {
        try {
            Optional<Nave> nave = navesService.getById(id);
            if (nave.isPresent()) {
                return new ResponseEntity<>(nave.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Crear una nueva nave", response = ResponseEntity.class)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Nave> createNave(@RequestBody NaveDto naveDto) {
        try {
            Nave createdNave = navesService.create(NavesMapper.INSTANCE.toEntity(naveDto));
            return new ResponseEntity<>(createdNave, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Actualizar una nave por su ID", response = ResponseEntity.class)
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Nave> updateNave(@PathVariable("id") Integer id, @RequestBody NaveDto naveDto) {
        try {
            Nave updatedNave = navesService.update(id, NavesMapper.INSTANCE.toEntity(naveDto));
            return new ResponseEntity<>(updatedNave, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Eliminar una nave por su ID", response = ResponseEntity.class)
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteNave(@PathVariable("id") Integer id) {
        try {
            navesService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Buscar una nave por su nombre parcial", response = ResponseEntity.class)
    @GetMapping(value = "/nombre/{nombre}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Nave>> getNaveByNombre(@PathVariable("nombre") String nombre) {
        try {
            List<Nave> naves = navesService.findByPartialNombre(nombre);
            if (!naves.isEmpty()) {
                return new ResponseEntity<>(naves, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Prueba de estado del servicio", response = HttpStatus.class)
    @GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus test() {
        try {
        	return HttpStatus.OK;
        } catch (Exception e) {
        	return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @ApiOperation(value = "Inicio de sesión", response = ResponseEntity.class)
    @GetMapping(value = "/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("EsTo.Es.Un.ToKeN");
    }
    
}