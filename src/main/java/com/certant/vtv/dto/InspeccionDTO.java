package com.certant.vtv.dto;

import java.time.LocalDate;

import com.certant.vtv.model.Estado;
import com.certant.vtv.model.Inspeccion;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InspeccionDTO {
	private Long id;
	private Estado resultadoFinal;
	private MedicionVisualDTO medicionVisual;
	private MedicionMaquinaDTO medicionMaquina;
	private Long vehiculoId;
	private Long inspectorId;
	private LocalDate fechaInspeccion;
	private Boolean esExento;

	@Getter
	@Setter
	@NoArgsConstructor
	public static class MedicionVisualDTO {
		private Estado luz;
		private Estado patente;
		private Estado espejos;
		private Estado chasis;
		private Estado vidrios;
		private Estado seguridadYemergencia;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class MedicionMaquinaDTO {
		private Estado suspension;
		private Estado direccion;
		private Estado trenDelantero;
	}

	public InspeccionDTO(Inspeccion inspeccion) {
		this.id = inspeccion.getId();
		this.fechaInspeccion = inspeccion.getFechaInspeccion();
		this.resultadoFinal = inspeccion.getResultadoFinal();
		this.medicionVisual = new MedicionVisualDTO();
		this.medicionVisual.setLuz(inspeccion.getMedicionVisual().getLuz());
		this.medicionVisual.setPatente(inspeccion.getMedicionVisual().getPatente());
		this.medicionVisual.setEspejos(inspeccion.getMedicionVisual().getEspejos());
		this.medicionVisual.setChasis(inspeccion.getMedicionVisual().getChasis());
		this.medicionVisual.setVidrios(inspeccion.getMedicionVisual().getVidrios());
		this.medicionVisual.setSeguridadYemergencia(inspeccion.getMedicionVisual().getSeguridadYemergencia());
		this.medicionMaquina = new MedicionMaquinaDTO();
		this.medicionMaquina.setSuspension(inspeccion.getMedicionMaquina().getSuspension());
		this.medicionMaquina.setDireccion(inspeccion.getMedicionMaquina().getDireccion());
		this.medicionMaquina.setTrenDelantero(inspeccion.getMedicionMaquina().getTrenDelantero());
		this.vehiculoId = inspeccion.getVehiculo().getId();
		this.inspectorId = inspeccion.getInspector().getId();

	}

}