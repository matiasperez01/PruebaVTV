package com.certant.vtv.service;

import java.util.List;

import com.certant.vtv.dto.VehiculoDTO;
import com.certant.vtv.model.Vehiculo;

public interface IVehiculoService {

	public List<VehiculoDTO> listarVehiculos();

	public VehiculoDTO listarVehiculoPorId(Long id);

	public VehiculoDTO listarVehiculoPorPatente(String patente);

	public List<VehiculoDTO> listarVehiculosPorPropietario(Long id);

	public VehiculoDTO editarVehiculo(Long id, VehiculoDTO dto);

	public void eliminarVehiculo(Long id);

	public VehiculoDTO crearVehiculo(VehiculoDTO dto);

	public VehiculoDTO convertirADTO(Vehiculo vehiculo);

	public void modificarEstadoVtv(Long id, String estado);

}
