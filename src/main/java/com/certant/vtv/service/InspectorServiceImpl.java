package com.certant.vtv.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.certant.vtv.dto.InspectorDTO;
import com.certant.vtv.model.Inspector;
import com.certant.vtv.repository.InspectorRepository;

import jakarta.transaction.Transactional;

@Service
public class InspectorServiceImpl implements IInspectorService {

	private final ModelMapper modelMapper = new ModelMapper();

	@Autowired
	private InspectorRepository inspectorRepository;

	@Override
	@Transactional
	public InspectorDTO crearInspector(InspectorDTO dto) {
		Inspector inspector = new Inspector();
		actualizarCamposDesdeDTO(dto, inspector);
		inspector.setEliminado(false);
		inspectorRepository.save(inspector);
		return convertirADTO(inspector);
	}

	@Override
	public List<InspectorDTO> listarInspectores() {
		List<InspectorDTO> inspectores = inspectorRepository.findByEliminadoFalse().stream().map(InspectorDTO::new)
				.collect(Collectors.toList());
		if (inspectores.isEmpty()) {
			throw new RuntimeException("No se encontraron inspectores registrados");
		} else
			return inspectores;
	}

	@Override
	public InspectorDTO buscarInspectorPorId(Long id) {
		Inspector inspector = inspectorRepository.findByIdAndEliminadoFalse(id)
				.orElseThrow(() -> new RuntimeException("No se encontro al inspector con el id " + id));
		return new InspectorDTO(inspector);
	}

	@Override
	@Transactional
	public InspectorDTO actualizarInspector(Long id, InspectorDTO dto) {
		Inspector inspector = inspectorRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("No se encontro al inspector con el id " + id));
		actualizarCamposDesdeDTO(dto, inspector);
		inspectorRepository.save(inspector);
		return new InspectorDTO(inspector);
	}

	@Override
	public void eliminarInspector(Long id) {
		Inspector inspector = inspectorRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("No se encontro al inspector con el id " + id));
		inspector.setEliminado(true);
		inspectorRepository.save(inspector);
	}

	@Override
	public InspectorDTO convertirADTO(Inspector inspector) {
		return modelMapper.map(inspector, InspectorDTO.class);
	}

	private void actualizarCamposDesdeDTO(InspectorDTO dto, Inspector inspector) {
		if (dto.getNombre() != null) {
			inspector.setNombre(dto.getNombre());
		}

		if (dto.getApellido() != null) {
			inspector.setApellido(dto.getApellido());
		}
		if (dto.getLegajo() != null) {
			inspector.setLegajo(dto.getLegajo());
		}
		if (dto.getEliminado() != null) {
			inspector.setEliminado(dto.getEliminado());
		}
	}

}
