package com.onemore.msnaves.naves.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.onemore.msnaves.common.GenericService;
import com.onemore.msnaves.common.MensajesService;
import com.onemore.msnaves.naves.db.INavesRepository;
import com.onemore.msnaves.naves.db.Nave;

@Service
public class NavesService extends GenericService<Nave, Integer> implements INavesService {

    private final INavesRepository navesRepository;
    private final MensajesService mensajesService;

    public NavesService(INavesRepository navesRepository, MensajesService mensajesService) {
        super(navesRepository);
        this.navesRepository = navesRepository;
        this.mensajesService = mensajesService;
    }

    @Cacheable(value = "naves", key = "'all'")
    @Override
    public List<Nave> getAll() {
        mensajesService.send("[" + LocalDate.now() + "] - " + "Se ha llamado a getAll");
        return navesRepository.findAllByOrderByIdAsc();
    }

    @Override
    public List<Nave> getAllPaginated(Pageable pageable) {
    	mensajesService.send("[" + LocalDate.now() + "] - " + "Se ha llamado a getAllPaginated");
    	return navesRepository.findAllPaginated(pageable.getPageSize(), (int)pageable.getOffset());
    }

    @Cacheable(value = "naves", key = "#id")
    @Override
    public Optional<Nave> getById(Integer id) {
        mensajesService.send("[" + LocalDate.now() + "] - " + "Se ha llamado a getById con id = " + id);
        return repository.findById(id);
    }

    @CacheEvict(value = "naves", key = "#result.id")
    @Override
    public Nave create(Nave entity) {
        mensajesService.send("[" + LocalDate.now() + "] - " + "Se ha llamado a create");
        return repository.save(entity);
    }

    @CacheEvict(value = "naves", key = "#id")
    @Override
    public Nave update(Integer id, Nave entity) {
        mensajesService.send("[" + LocalDate.now() + "] - " + "Se ha llamado a update con id = " + id);
        Nave existingEntity = navesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(entity.getClass().getSimpleName() + " not found with id: " + id));
        existingEntity.setNombre(entity.getNombre());
        return navesRepository.save(existingEntity);
    }

    @CacheEvict(value = "naves", key = "#id")
    @Override
    public void delete(Integer id) {
        mensajesService.send("[" + LocalDate.now() + "] - " + "Se ha llamado a delete con id = " + id);
        Nave entityToDelete = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entity not found with id: " + id));
        repository.delete(entityToDelete);
    }

    @Cacheable(value = "naves", key = "#nombre")
    public List<Nave> findByNombre(String nombre) {
        mensajesService.send("[" + LocalDate.now() + "] - " + "Se ha llamado a findByNombre con nombre = " + nombre);
        return navesRepository.findByNombre(nombre);
    }
    
    @Cacheable(value = "naves", key = "#nombre")
    public List<Nave> findByPartialNombre(String nombre) {
        mensajesService.send("[" + LocalDate.now() + "] - " + "Se ha llamado a findByPartialNombre con nombre = " + nombre);
        return navesRepository.findByPartialNombre(nombre);
    }

}

