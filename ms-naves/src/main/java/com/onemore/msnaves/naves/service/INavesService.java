package com.onemore.msnaves.naves.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.onemore.msnaves.naves.db.Nave;

public interface INavesService {
	
	List<Nave> getAllPaginated(Pageable pageable);
	List<Nave> findByNombre(String nombre);
	List<Nave> findByPartialNombre(String nombre);
	
}
