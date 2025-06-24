package com.certant.vtv.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inspeccion")
public class Inspeccion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "fecha_inspeccion")
	private LocalDate fechaInspeccion;

	@Column(name = "es_exento")
	private Boolean esExento;

	@Column(name = "eliminado")
	private Boolean eliminado;

	@ManyToOne
	@JoinColumn(name = "inspector_id")
	private Inspector inspector;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vehiculo_id")
	@JsonIgnore
	private Vehiculo vehiculo;

	@OneToOne(mappedBy = "inspeccion", cascade = CascadeType.ALL, orphanRemoval = true)
	private MedicionesVisuales medicionVisual;

	@OneToOne(mappedBy = "inspeccion", cascade = CascadeType.ALL, orphanRemoval = true)
	private MedicionesMaquina medicionMaquina;

	@Enumerated(EnumType.STRING)
	private Estado resultadoFinal;
}
