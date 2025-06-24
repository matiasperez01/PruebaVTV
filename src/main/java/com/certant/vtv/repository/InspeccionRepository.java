package com.certant.vtv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.certant.vtv.model.Inspeccion;

@Repository
public interface InspeccionRepository extends JpaRepository<Inspeccion, Long> {
	List<Inspeccion> findByEliminadoFalse();
}
