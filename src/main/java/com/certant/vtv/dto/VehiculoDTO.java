package com.certant.vtv.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.certant.vtv.model.Inspeccion;
import com.certant.vtv.model.Vehiculo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VehiculoDTO {
	private Long id;
	private String patente;
	private String marca;
	private String modelo;
	private Integer anio;
	private LocalDate vencimientoVtv;
	private String estado;
	private Long propietarioDTOId;
	@JsonIgnore
	private List<Inspeccion> inspecciones;

	public VehiculoDTO(Vehiculo vehiculo) {
		super();
		this.id = vehiculo.getId();
		this.patente = vehiculo.getPatente();
		this.marca = vehiculo.getMarca();
		this.modelo = vehiculo.getModelo();
		this.anio = vehiculo.getAnio();
		this.vencimientoVtv = vehiculo.getVencimientoVtv();
		this.estado = vehiculo.getEstado().toString();
		this.propietarioDTOId = vehiculo.getPropietario().getId();
		this.inspecciones = vehiculo.getInspecciones() != null ? vehiculo.getInspecciones() : new ArrayList<>();
	}

}
