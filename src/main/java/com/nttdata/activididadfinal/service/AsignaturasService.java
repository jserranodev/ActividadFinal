package com.nttdata.activididadfinal.service;

import java.util.List;

import com.nttdata.activididadfinal.repository.entity.Asignaturas;

public interface AsignaturasService {
	public List<Asignaturas> listar();

	public Asignaturas buscarPorId(Integer id);

	public void eliminar();

	public Asignaturas borrarPorId(Integer id);

	public Asignaturas actualizar(Asignaturas asig);

	public Asignaturas crear(Asignaturas asig);
}
