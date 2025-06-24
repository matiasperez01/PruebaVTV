package com.certant.vtv.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "mediciones_maquina")
public class MedicionesMaquina {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "suspension")
	@Enumerated(EnumType.STRING)
	private Estado suspension;

	@Column(name = "tren_delantero")
	@Enumerated(EnumType.STRING)
	private Estado trenDelantero;

	@Column(name = "direccion")
	@Enumerated(EnumType.STRING)
	private Estado direccion;

	@OneToOne
	@JoinColumn(name = "inspeccion_id")
	private Inspeccion inspeccion;
}

/*
 * { // Se calcula automáticamente si no se envía
 * "medicionVisual":{"luz":"APTO","patente":"CONDICIONAL", // Ejemplo de
 * componente condicional
 * "espejos":"APTO","chasis":"APTO","vidrios":"APTO","seguridadYemergencia":
 * "RECHAZADO" // Ejemplo de rechazo
 * },"medicionMaquina":{"suspension":"APTO","direccion":"APTO","trenDelantero":
 * "CONDICIONAL"},"vehiculoId":2, // ID del // vehículo // inspeccionado //
 * (requerido) "inspectorId":1 // ID del inspector asignado (requerido) }
 * 
 * {"patente":"ABC123","marca":"Toyota","modelo":"Corolla","anio":2020,
 * "propietarioDTOId":1 // Solo necesitas el ID del // propietario }
 */