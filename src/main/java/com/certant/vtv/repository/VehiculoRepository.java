package com.certant.vtv.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.certant.vtv.model.Vehiculo;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
	public List<Vehiculo> findByPropietarioId(Long id);

	public List<Vehiculo> findByEliminadoFalse();

	public Optional<Vehiculo> findByIdAndEliminadoFalse(Long id);

	public Vehiculo findByPatenteAndEliminadoFalse(String patente);
}
