package com.certant.vtv.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.certant.vtv.model.Propietario;

@Repository
public interface PropietarioRepository extends JpaRepository<Propietario, Long> {
	public List<Propietario> findByEliminadoFalse();

	public Optional<Propietario> findByIdAndEliminadoFalse(Long id);

}
