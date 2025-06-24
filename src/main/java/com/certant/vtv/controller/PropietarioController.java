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

import com.certant.vtv.dto.PropietarioDTO;
import com.certant.vtv.service.PropietarioServiceImpl;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class PropietarioController {

	@Autowired
	private PropietarioServiceImpl propietarioService;

	@PostMapping("/propietarios")
	public ResponseEntity<PropietarioDTO> crearPropietario(@RequestBody PropietarioDTO propietarioDto) {
		PropietarioDTO dto = propietarioService.crearPropietario(propietarioDto);
		return ResponseEntity.status(201).body(dto);
	}

	@GetMapping("/propietarios")
	public ResponseEntity<List<PropietarioDTO>> listarPropietarios() {
		return ResponseEntity.ok(propietarioService.listarPropietarios());
	}

	@GetMapping("/propietarios/{id}")
	public ResponseEntity<PropietarioDTO> listarPropietarioPorId(@PathVariable Long id) {
		return ResponseEntity.ok(propietarioService.buscarPropietarioPorId(id));
	}

	@PutMapping("/propietarios/{id}")
	public ResponseEntity<PropietarioDTO> actualizarPropietario(@PathVariable Long id,
			@RequestBody PropietarioDTO dto) {
		PropietarioDTO propActualizar = propietarioService.actualizarPropietario(id, dto);
		return ResponseEntity.ok(propActualizar);
	}

	@DeleteMapping("/propietarios/{id}")
	public ResponseEntity<Void> eliminarPropietario(@PathVariable Long id) {
		propietarioService.eliminarPropietario(id);
		return ResponseEntity.noContent().build();
	}
}
