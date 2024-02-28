package com.onemore.msnaves.common;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public abstract class GenericService<T, ID> implements IGenericService<T, ID> {

    protected JpaRepository<T, ID> repository;

    public GenericService(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    public List<T> getAll() {
        return repository.findAll();
    }

    public Optional<T> getById(ID id) {
        return repository.findById(id);
    }

    public T create(T entity) {
        return repository.save(entity);
    }

    public T update(ID id, T entity) {
        T existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(entity.getClass().getSimpleName() + " not found with id: " + id));
        return repository.save(existingEntity);
    }

    public void delete(ID id) {
        T entityToDelete = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entity not found with id: " + id));
        repository.delete(entityToDelete);
    }
    
}
