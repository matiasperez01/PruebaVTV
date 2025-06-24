package com.certant.vtv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.certant.vtv.dto.VehiculoDTO;
import com.certant.vtv.service.VehiculoServiceImpl;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class VehiculoController {

	@Autowired
	private VehiculoServiceImpl vehiculoService;

	@PostMapping("/vehiculos")
	public ResponseEntity<VehiculoDTO> crearVehiculo(@RequestBody VehiculoDTO vehiculoDTO) {
		VehiculoDTO dto = vehiculoService.crearVehiculo(vehiculoDTO);
		return ResponseEntity.status(201).body(dto);
	}

	@GetMapping("/vehiculos")
	public ResponseEntity<List<VehiculoDTO>> listarVehiculos() {
		return ResponseEntity.ok(vehiculoService.listarVehiculos());
	}

	@GetMapping("/vehiculos/propietario/{id_propietario}")
	public ResponseEntity<List<VehiculoDTO>> listarVehiculosDeUnPropietario(@PathVariable Long id_propietario) {
		return ResponseEntity.ok(vehiculoService.listarVehiculosPorPropietario(id_propietario));
	}

	@PutMapping("/vehiculos/{id}")
	public ResponseEntity<VehiculoDTO> actualizarVehiculo(@PathVariable Long id, @RequestBody VehiculoDTO dto) {
		VehiculoDTO vehiculoPorActualizar = vehiculoService.editarVehiculo(id, dto);
		return ResponseEntity.ok(vehiculoPorActualizar);
	}

	@DeleteMapping("/vehiculos/{id}")
	public ResponseEntity<Void> eliminarVehiculo(@PathVariable Long id) {
		vehiculoService.eliminarVehiculo(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/vehiculos/{id}/modificar-estado")
	public ResponseEntity<Void> modificarEstadoVtv(@PathVariable Long id, @RequestParam String estado) {
		vehiculoService.modificarEstadoVtv(id, estado);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/vehiculos/id/{id}")
	public ResponseEntity<VehiculoDTO> listarVehiculoPorId(@PathVariable Long id) {
		return ResponseEntity.ok(vehiculoService.listarVehiculoPorId(id));
	}

	@GetMapping("/vehiculos/patente/{patente}")
	public ResponseEntity<VehiculoDTO> listarVehiculoPorPatente(@PathVariable String patente) {
		return ResponseEntity.ok(vehiculoService.listarVehiculoPorPatente(patente));
	}

}
