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
@Table(name = "mediciones_visuales")
public class MedicionesVisuales {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "luz")
	@Enumerated(EnumType.STRING)
	private Estado luz;

	@Column(name = "patente")
	@Enumerated(EnumType.STRING)
	private Estado patente;

	@Column(name = "espejos")
	@Enumerated(EnumType.STRING)
	private Estado espejos;

	@Column(name = "chasis")
	@Enumerated(EnumType.STRING)
	private Estado chasis;

	@Column(name = "vidrios")
	@Enumerated(EnumType.STRING)
	private Estado vidrios;

	@Column(name = "seguridad_emergencia")
	@Enumerated(EnumType.STRING)
	private Estado seguridadYemergencia;

	@OneToOne
	@JoinColumn(name = "inspeccion_id")
	private Inspeccion inspeccion;

}
