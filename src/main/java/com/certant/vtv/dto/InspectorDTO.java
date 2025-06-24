package com.certant.vtv.dto;

import java.util.ArrayList;
import java.util.List;

import com.certant.vtv.model.Inspeccion;
import com.certant.vtv.model.Inspector;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InspectorDTO {
	private Long id;
	private String nombre;
	private String apellido;
	private Boolean eliminado = false;
	private Long legajo;
	@JsonIgnore
	private List<Inspeccion> inspecciones;

	public InspectorDTO(Inspector inspector) {
		this.id = inspector.getId();
		this.nombre = inspector.getNombre();
		this.apellido = inspector.getApellido();
		this.eliminado = inspector.getEliminado();
		this.legajo = inspector.getLegajo();
		this.inspecciones = inspector.getInspecciones() != null ? inspector.getInspecciones() : new ArrayList<>();
	}
}
