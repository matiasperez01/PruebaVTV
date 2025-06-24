package com.certant.vtv.service;

import java.util.List;

import com.certant.vtv.dto.InspectorDTO;
import com.certant.vtv.model.Inspector;

public interface IInspectorService {
	InspectorDTO crearInspector(InspectorDTO dto);

	List<InspectorDTO> listarInspectores();

	InspectorDTO buscarInspectorPorId(Long id);

	InspectorDTO actualizarInspector(Long id, InspectorDTO dto);

	void eliminarInspector(Long id);

	public InspectorDTO convertirADTO(Inspector inspector);
}
