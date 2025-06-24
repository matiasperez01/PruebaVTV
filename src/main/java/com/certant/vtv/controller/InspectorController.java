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

import com.certant.vtv.dto.InspectorDTO;
import com.certant.vtv.service.InspectorServiceImpl;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class InspectorController {
	@Autowired
	private InspectorServiceImpl inspectorService;

	@PostMapping("/inspectores")
	public ResponseEntity<InspectorDTO> crearInspector(@RequestBody InspectorDTO inspectorDTO) {
		InspectorDTO dto = inspectorService.crearInspector(inspectorDTO);
		return ResponseEntity.status(201).body(dto);
	}

	@GetMapping("/inspectores")
	public ResponseEntity<List<InspectorDTO>> listarInspectores() {
		return ResponseEntity.ok(inspectorService.listarInspectores());
	}

	@GetMapping("/inspectores/{id}")
	public ResponseEntity<InspectorDTO> listarInspectorPorId(@PathVariable Long id) {
		return ResponseEntity.ok(inspectorService.buscarInspectorPorId(id));
	}

	@PutMapping("/inspectores/{id}")
	public ResponseEntity<InspectorDTO> actualizarInspector(@PathVariable Long id, @RequestBody InspectorDTO dto) {
		InspectorDTO inspActualizar = inspectorService.actualizarInspector(id, dto);
		return ResponseEntity.ok(inspActualizar);
	}

	@DeleteMapping("/inspectores/{id}")
	public ResponseEntity<Void> eliminarInspector(@PathVariable Long id) {
		inspectorService.eliminarInspector(id);
		return ResponseEntity.noContent().build();
	}

}
