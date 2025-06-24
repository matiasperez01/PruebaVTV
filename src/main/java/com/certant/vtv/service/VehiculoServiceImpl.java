package com.certant.vtv.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.certant.vtv.dto.VehiculoDTO;
import com.certant.vtv.model.Estado;
import com.certant.vtv.model.Propietario;
import com.certant.vtv.model.Vehiculo;
import com.certant.vtv.repository.PropietarioRepository;
import com.certant.vtv.repository.VehiculoRepository;

import jakarta.transaction.Transactional;

@Service
public class VehiculoServiceImpl implements IVehiculoService {
	private final ModelMapper modelMapper = new ModelMapper();
	@Autowired
	private VehiculoRepository vehiculoRepository;

	@Autowired
	private PropietarioRepository propietarioRepository;

	@Override
	public List<VehiculoDTO> listarVehiculos() {
		List<VehiculoDTO> vehiculos = vehiculoRepository.findByEliminadoFalse().stream().map(VehiculoDTO::new)
				.collect(Collectors.toList());
		if (vehiculos.isEmpty()) {
			throw new RuntimeException("No se encontraron vehiculos registrados");
		} else
			return vehiculos;
	}

	@Override
	public VehiculoDTO listarVehiculoPorId(Long id) {
		Vehiculo vehiculo = vehiculoRepository.findByIdAndEliminadoFalse(id)
				.orElseThrow(() -> new RuntimeException("No se encontro al vehiculo con el id " + id));
		return new VehiculoDTO(vehiculo);
	}

	@Override
	public VehiculoDTO listarVehiculoPorPatente(String patente) {
		String patentePorBuscar = patente.toUpperCase();
		Vehiculo vehiculo = vehiculoRepository.findByPatenteAndEliminadoFalse(patentePorBuscar);
		if (vehiculo == null) {
			throw new RuntimeException("No se encontro al vehiculo con la patente " + patente);
		} else
			return convertirADTO(vehiculo);
	}

	@Override
	public List<VehiculoDTO> listarVehiculosPorPropietario(Long id) {
		List<VehiculoDTO> vehiculos = vehiculoRepository.findByPropietarioId(id).stream().map(VehiculoDTO::new)
				.collect(Collectors.toList());
		if (vehiculos.isEmpty()) {
			throw new RuntimeException("No se encontraron vehiculos registrados para el propietario con el ID " + id);
		} else
			return vehiculos;
	}

	@Override
	@Transactional
	public VehiculoDTO editarVehiculo(Long id, VehiculoDTO dto) {
		Vehiculo vehiculo = vehiculoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("No se encontro al vehiculo con el id " + id));
		actualizarCamposDesdeDTO(dto, vehiculo);
		vehiculoRepository.save(vehiculo);
		return new VehiculoDTO(vehiculo);
	}

	@Override
	public void eliminarVehiculo(Long id) {
		Vehiculo vehiculo = vehiculoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("No se encontro al vehiculo con el id " + id));
		vehiculo.setEliminado(true);
		vehiculoRepository.save(vehiculo);
	}

	@Override
	@Transactional
	public VehiculoDTO crearVehiculo(VehiculoDTO dto) {
		Vehiculo vehiculo = new Vehiculo();
		actualizarCamposDesdeDTO(dto, vehiculo);
		vehiculo.setEliminado(false);
		vehiculo.setEstado(Estado.CONDICIONAL);
		vehiculoRepository.save(vehiculo);
		return convertirADTO(vehiculo);
	}

	@Override
	public VehiculoDTO convertirADTO(Vehiculo vehiculo) {
		return modelMapper.map(vehiculo, VehiculoDTO.class);
	}

	@Override
	public void modificarEstadoVtv(Long id, String estado) {
		Vehiculo vehiculo = vehiculoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("No se encontro al vehiculo con el id " + id));
		vehiculo.setEstado(tryParseEstado(estado));
		vehiculoRepository.save(vehiculo);
	}

	private void actualizarCamposDesdeDTO(VehiculoDTO dto, Vehiculo vehiculo) {
		if (dto.getPatente() != null || dto.getPatente().isBlank()) {
			vehiculo.setPatente(dto.getPatente());
		}
		if (dto.getMarca() != null || dto.getMarca().isBlank()) {
			vehiculo.setMarca(dto.getMarca());
		}
		if (dto.getModelo() != null || dto.getModelo().isBlank()) {
			vehiculo.setModelo(dto.getModelo());
		}
		if (dto.getEstado() != null) {
			vehiculo.setEstado(tryParseEstado(dto.getEstado()));
		}
		if (dto.getAnio() != null) {
			vehiculo.setAnio(dto.getAnio());
		}

		Propietario propietario = propietarioRepository.findById(vehiculo.getPropietario().getId())
				.orElseThrow(() -> new RuntimeException("Propietario no encontrado"));
		vehiculo.setPropietario(propietario);

	}

	private Estado tryParseEstado(String estado) {
		try {
			return Estado.valueOf(estado.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Tipo de estado inválido: " + estado);
		}
	}

}
