package com.certant.vtv.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.certant.vtv.model.Propietario;
import com.certant.vtv.model.Vehiculo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class PropietarioDTO {
	private Long id;
	private String nombre;
	private String apellido;
	private Long dni;
	private Boolean esExento = false;
	private Boolean eliminado = false;
	private List<Vehiculo> vehiculos;
	private LocalDate fecha_nacimiento;

	public PropietarioDTO(Propietario propietario) {
		this.id = propietario.getId();
		this.nombre = propietario.getNombre();
		this.apellido = propietario.getApellido();
		this.dni = propietario.getDni();
		this.esExento = propietario.getEsExento();
		this.fecha_nacimiento = propietario.getFecha_nacimiento();
		this.eliminado = propietario.getEliminado();
		this.vehiculos = propietario.getVehiculos() != null ? propietario.getVehiculos() : new ArrayList<>();
	}

}
