package com.certant.vtv.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "vehiculo")
public class Vehiculo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "patente", unique = true)
	private String patente;

	@Column(name = "marca")
	private String marca;

	@Column(name = "modelo")
	private String modelo;

	@Column(name = "anio")
	private Integer anio;

	@Column(name = "vencimiento_vtv")
	private LocalDate vencimientoVtv;

	@Column(name = "estado")
	@Enumerated(EnumType.STRING)
	private Estado estado;

	@Column(name = "eliminado")
	private Boolean eliminado;

	@ManyToOne
	@JoinColumn(name = "propietario_id", nullable = false)
	@JsonIgnore
	private Propietario propietario;

	@OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Inspeccion> inspecciones;
}
