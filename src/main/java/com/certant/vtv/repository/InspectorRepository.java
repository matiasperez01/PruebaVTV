package com.certant.vtv.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.certant.vtv.model.Inspector;

@Repository
public interface InspectorRepository extends JpaRepository<Inspector, Long> {
	public List<Inspector> findByEliminadoFalse();

	public Optional<Inspector> findByIdAndEliminadoFalse(Long id);
}
