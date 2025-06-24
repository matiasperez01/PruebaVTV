package com.certant.vtv.service;

import java.util.List;

import com.certant.vtv.dto.InspeccionDTO;
import com.certant.vtv.model.Inspeccion;

public interface IInspeccionService {

	InspeccionDTO crearInspeccion(InspeccionDTO inspeccionDTO);

	InspeccionDTO listarInspeccionPorId(Long id);

	List<InspeccionDTO> listarInspecciones();

	InspeccionDTO actualizarInspeccion(Long id, InspeccionDTO inspeccionDTO);

	void eliminarInspeccion(Long id);

	InspeccionDTO convertirADTO(Inspeccion inspeccion);
}
