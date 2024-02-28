package com.onemore.msnaves.naves.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface INavesRepository extends JpaRepository<Nave, Integer> {
	
	List<Nave> findAllByOrderByIdAsc();
	
	@Query(value = "SELECT * FROM naves n LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Nave> findAllPaginated(@Param("limit") int limit, @Param("offset") int offset);
	
	List<Nave> findByNombre(String nombre);
	
	@Query("SELECT n FROM Nave n WHERE n.nombre LIKE %:nombre%")
    List<Nave> findByPartialNombre(@Param("nombre") String nombre);
	
}