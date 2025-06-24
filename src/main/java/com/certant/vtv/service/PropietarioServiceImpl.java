package com.certant.vtv.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.certant.vtv.dto.PropietarioDTO;
import com.certant.vtv.model.Propietario;
import com.certant.vtv.repository.PropietarioRepository;

import jakarta.transaction.Transactional;

@Service
public class PropietarioServiceImpl implements IPropietarioService {
	private final ModelMapper modelMapper = new ModelMapper();

	@Autowired
	private PropietarioRepository propietarioRepository;

	@Override
	@Transactional
	public PropietarioDTO crearPropietario(PropietarioDTO dto) {
		Propietario propietario = new Propietario();
		actualizarCamposDesdeDTO(dto, propietario);
		propietarioRepository.save(propietario);
		return convertirADTO(propietario);
	}

	@Override
	public List<PropietarioDTO> listarPropietarios() {
		List<PropietarioDTO> propietarios = propietarioRepository.findByEliminadoFalse().stream()
				.map(PropietarioDTO::new).collect(Collectors.toList());
		if (propietarios.isEmpty()) {
			throw new RuntimeException("No se encontraron propietarios registrados");
		} else
			return propietarios;
	}

	@Override
	public PropietarioDTO buscarPropietarioPorId(Long id) {
		Propietario propietario = propietarioRepository.findByIdAndEliminadoFalse(id)
				.orElseThrow(() -> new RuntimeException("No se encontro al propietario con el id " + id));
		return new PropietarioDTO(propietario);
	}

	@Override
	@Transactional
	public PropietarioDTO actualizarPropietario(Long id, PropietarioDTO dto) {
		Propietario propietario = propietarioRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("No se encontro al propietario con el id " + id));
		actualizarCamposDesdeDTO(dto, propietario);
		propietarioRepository.save(propietario);
		return new PropietarioDTO(propietario);
	}

	@Override
	public void eliminarPropietario(Long id) {
		Propietario propietario = propietarioRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("No se encontro al propietario con el id " + id));
		propietario.setEliminado(true);
		propietarioRepository.save(propietario);
	}

	@Override
	public PropietarioDTO convertirADTO(Propietario propietario) {
		return modelMapper.map(propietario, PropietarioDTO.class);
	}

	private void actualizarCamposDesdeDTO(PropietarioDTO dto, Propietario propietario) {
		if (dto.getNombre() != null) {
			propietario.setNombre(dto.getNombre());
		}

		if (dto.getApellido() != null) {
			propietario.setApellido(dto.getApellido());
		}
		if (dto.getFecha_nacimiento() != null) {
			propietario.setFecha_nacimiento(dto.getFecha_nacimiento());
		}
		if (dto.getDni() != null) {
			propietario.setDni(dto.getDni());
		}
		if (dto.getEsExento() != null) {
			propietario.setEsExento(dto.getEsExento());
		}
	}

}
