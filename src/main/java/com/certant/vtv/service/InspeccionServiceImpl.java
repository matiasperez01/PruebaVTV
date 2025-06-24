package com.certant.vtv.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.certant.vtv.dto.InspeccionDTO;
import com.certant.vtv.model.Estado;
import com.certant.vtv.model.Inspeccion;
import com.certant.vtv.model.Inspector;
import com.certant.vtv.model.MedicionesMaquina;
import com.certant.vtv.model.MedicionesVisuales;
import com.certant.vtv.model.Propietario;
import com.certant.vtv.model.Vehiculo;
import com.certant.vtv.repository.InspeccionRepository;
import com.certant.vtv.repository.InspectorRepository;
import com.certant.vtv.repository.PropietarioRepository;
import com.certant.vtv.repository.VehiculoRepository;

import jakarta.transaction.Transactional;

@Service
public class InspeccionServiceImpl implements IInspeccionService {
	private ModelMapper modelMapper = new ModelMapper();;
	@Autowired
	private InspeccionRepository inspeccionRepository;
	@Autowired
	private VehiculoRepository vehiculoRepository;
	@Autowired
	private InspectorRepository inspectorRepository;
	@Autowired
	private PropietarioRepository propietarioRepository;

	@Override
	@Transactional
	public InspeccionDTO crearInspeccion(InspeccionDTO dto) {
		Inspeccion inspeccion = new Inspeccion();

		MedicionesVisuales medicionVisual = new MedicionesVisuales();
		actualizarMedicionVisual(dto.getMedicionVisual(), medicionVisual);
		inspeccion.setMedicionVisual(medicionVisual);
		medicionVisual.setInspeccion(inspeccion);

		MedicionesMaquina medicionMaquina = new MedicionesMaquina();
		actualizarMedicionMaquina(dto.getMedicionMaquina(), medicionMaquina);
		inspeccion.setMedicionMaquina(medicionMaquina);
		medicionMaquina.setInspeccion(inspeccion);

		Estado estadoFinal = calcularResultadoFinal(medicionVisual, medicionMaquina);

		if (dto.getVehiculoId() == null) {
			throw new IllegalArgumentException("El ID del vehiculo no puede ser nulo");
		}
		Vehiculo vehiculo = vehiculoRepository.findById(dto.getVehiculoId())
				.orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
		vehiculo.setEstado(estadoFinal);
		if (estadoFinal == Estado.APTO) {
			vehiculo.setVencimientoVtv(LocalDate.now().plusYears(1));
		}
		if (estadoFinal == Estado.CONDICIONAL) {
			vehiculo.setVencimientoVtv(LocalDate.now().plusDays(1));
		}
		vehiculoRepository.save(vehiculo);
		inspeccion.setVehiculo(vehiculo);
		Inspector inspector = inspectorRepository.findByIdAndEliminadoFalse(dto.getInspectorId())
				.orElseThrow(() -> new RuntimeException("Inspector no encontrado"));
		inspeccion.setInspector(inspector);

		Propietario propietario = propietarioRepository.findByIdAndEliminadoFalse(vehiculo.getPropietario().getId())
				.orElseThrow(() -> new RuntimeException("Propietario no encontrado"));
		inspeccion.setEsExento(propietario.getEsExento());

		// Calcular y guardar
		inspeccion.setResultadoFinal(estadoFinal);
		inspeccion.setFechaInspeccion(LocalDate.now());

		inspeccionRepository.save(inspeccion);
		return new InspeccionDTO(inspeccion);
	}

	@Override
	public InspeccionDTO listarInspeccionPorId(Long id) {
		Inspeccion inspeccion = inspeccionRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Inspección no encontrada"));
		return new InspeccionDTO(inspeccion);
	}

	@Override
	public List<InspeccionDTO> listarInspecciones() {
		List<Inspeccion> inspecciones = inspeccionRepository.findAll();

		/*
		 * if (inspecciones.isEmpty()) { throw new
		 * RuntimeException("No se encontraron inspecciones registradas"); } else
		 */
		return inspecciones.stream().map(InspeccionDTO::new).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public InspeccionDTO actualizarInspeccion(Long id, InspeccionDTO dto) {
		System.out.println("DTO recibido: " + dto);
		Inspeccion inspeccion = inspeccionRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Inspección no encontrada"));
		actualizarCamposDesdeDTO(dto, inspeccion);
		Estado estadoFinal = calcularResultadoFinal(inspeccion.getMedicionVisual(), inspeccion.getMedicionMaquina());
		inspeccion.setResultadoFinal(estadoFinal);
		Vehiculo vehiculo = vehiculoRepository.findById(dto.getVehiculoId())
				.orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
		vehiculo.setEstado(estadoFinal);
		if (estadoFinal == Estado.APTO) {
			vehiculo.setVencimientoVtv(LocalDate.now().plusYears(1));
		}
		if (estadoFinal == Estado.CONDICIONAL) {
			vehiculo.setVencimientoVtv(LocalDate.now().plusDays(1));
		}
		vehiculoRepository.save(vehiculo);
		inspeccion.setVehiculo(vehiculo);
		inspeccionRepository.save(inspeccion);
		return convertirADTO(inspeccion);
	}

	@Override
	public void eliminarInspeccion(Long id) {
		Inspeccion inspeccion = inspeccionRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("No se encontro la inspección con el id " + id));
		inspeccion.setEliminado(true);
		inspeccionRepository.save(inspeccion);
	}

	@Override
	public InspeccionDTO convertirADTO(Inspeccion inspeccion) {
		return modelMapper.map(inspeccion, InspeccionDTO.class);
	}

	private void actualizarCamposDesdeDTO(InspeccionDTO dto, Inspeccion inspeccion) {
		if (dto.getResultadoFinal() != null) {
			inspeccion.setResultadoFinal(tryParseEstado(dto.getResultadoFinal()));
		}

		if (dto.getMedicionVisual() != null) {
			if (inspeccion.getMedicionVisual() == null) {
				inspeccion.setMedicionVisual(new MedicionesVisuales());
			}
			actualizarMedicionVisual(dto.getMedicionVisual(), inspeccion.getMedicionVisual());
		}
		if (dto.getMedicionMaquina() != null) {
			if (inspeccion.getMedicionMaquina() == null) {
				inspeccion.setMedicionMaquina(new MedicionesMaquina());
			}
			actualizarMedicionMaquina(dto.getMedicionMaquina(), inspeccion.getMedicionMaquina());
		}

	}

	private void actualizarMedicionVisual(InspeccionDTO.MedicionVisualDTO dto, MedicionesVisuales entity) {
		if (dto.getLuz() != null)
			entity.setLuz(tryParseEstado(dto.getLuz()));
		if (dto.getPatente() != null)
			entity.setPatente(tryParseEstado(dto.getPatente()));
		if (dto.getEspejos() != null)
			entity.setEspejos(tryParseEstado(dto.getEspejos()));
		if (dto.getVidrios() != null)
			entity.setVidrios(tryParseEstado(dto.getVidrios()));
		if (dto.getChasis() != null)
			entity.setChasis(tryParseEstado(dto.getChasis()));
		if (dto.getSeguridadYemergencia() != null)
			entity.setSeguridadYemergencia(tryParseEstado(dto.getSeguridadYemergencia()));
	}

	private void actualizarMedicionMaquina(InspeccionDTO.MedicionMaquinaDTO dto, MedicionesMaquina entity) {
		if (dto.getSuspension() != null)
			entity.setSuspension(tryParseEstado(dto.getSuspension()));
		if (dto.getDireccion() != null)
			entity.setDireccion(tryParseEstado(dto.getDireccion()));
		if (dto.getTrenDelantero() != null)
			entity.setTrenDelantero(tryParseEstado(dto.getTrenDelantero()));
	}

	private Estado calcularResultadoFinal(MedicionesVisuales visual, MedicionesMaquina maquina) {
		List<Estado> todosEstados = new ArrayList<>();

		if (visual != null) {
			todosEstados.addAll(Arrays.asList(visual.getLuz(), visual.getPatente(), visual.getEspejos(),
					visual.getChasis(), visual.getVidrios(), visual.getSeguridadYemergencia()));
		}

		if (maquina != null) {
			todosEstados
					.addAll(Arrays.asList(maquina.getSuspension(), maquina.getDireccion(), maquina.getTrenDelantero()));
		}

		if (todosEstados.contains(Estado.RECHAZADO)) {
			return Estado.RECHAZADO;
		}

		if (todosEstados.contains(Estado.CONDICIONAL)) {
			return Estado.CONDICIONAL;
		}

		return Estado.APTO;
	}

	private Estado tryParseEstado(Object estado) {
		if (estado == null) {
			throw new IllegalArgumentException("El estado no puede ser nulo");
		}

		if (estado instanceof Estado) {
			return (Estado) estado;
		}

		if (estado instanceof String) {
			try {
				return Estado.valueOf(((String) estado).toUpperCase());
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("Estado inválido: " + estado);
			}
		}

		throw new IllegalArgumentException("Tipo de estado no soportado: " + estado.getClass());
	}

}
