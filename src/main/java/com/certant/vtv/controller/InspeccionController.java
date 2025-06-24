package com.certant.vtv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.certant.vtv.dto.InspeccionDTO;
import com.certant.vtv.service.InspeccionServiceImpl;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class InspeccionController {

	@Autowired
	private InspeccionServiceImpl inspeccionService;

	@PostMapping("/inspecciones")
	public ResponseEntity<InspeccionDTO> generarInspeccion(@RequestBody InspeccionDTO inspeccionDTO) {
		InspeccionDTO dto = inspeccionService.crearInspeccion(inspeccionDTO);
		return ResponseEntity.status(201).body(dto);
	}

	@GetMapping("/inspecciones")
	public ResponseEntity<List<InspeccionDTO>> listarInspecciones() {
		return ResponseEntity.ok(inspeccionService.listarInspecciones());
	}

	/*
	 * @GetMapping("/inspecciones/vehiculo/{patente}") public
	 * ResponseEntity<List<InspeccionDTO>>
	 * listarInspeccionesDeUnVehiculo(@PathVariable String patente) { return
	 * ResponseEntity.ok(inspeccionService.listarInspeccionesDeUnVehiculo(patente));
	 * }
	 */

	@PutMapping("/inspecciones/{id}")
	public ResponseEntity<InspeccionDTO> actualizarInspeccion(@PathVariable Long id, @RequestBody InspeccionDTO dto) {
		InspeccionDTO inspeccionPorActualizar = inspeccionService.actualizarInspeccion(id, dto);
		return ResponseEntity.ok(inspeccionPorActualizar);
	}

	@DeleteMapping("/inspecciones/{id}")
	public ResponseEntity<Void> eliminarInspeccion(@PathVariable Long id) {
		inspeccionService.eliminarInspeccion(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/inspecciones/id/{id}")
	public ResponseEntity<InspeccionDTO> listarInspeccionPorId(@PathVariable Long id) {
		return ResponseEntity.ok(inspeccionService.listarInspeccionPorId(id));
	}
}
