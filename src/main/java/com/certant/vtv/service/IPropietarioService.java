package com.certant.vtv.service;

import java.util.List;

import com.certant.vtv.dto.PropietarioDTO;
import com.certant.vtv.model.Propietario;

public interface IPropietarioService {

	PropietarioDTO crearPropietario(PropietarioDTO dto);

	List<PropietarioDTO> listarPropietarios();

	PropietarioDTO buscarPropietarioPorId(Long id);

	PropietarioDTO actualizarPropietario(Long id, PropietarioDTO dto);

	void eliminarPropietario(Long id);

	public PropietarioDTO convertirADTO(Propietario propietario);
}
