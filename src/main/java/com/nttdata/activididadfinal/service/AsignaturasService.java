package com.nttdata.activididadfinal.service;

import java.util.List;

import com.nttdata.activididadfinal.repository.entity.Asignaturas;

public interface AsignaturasService {
	public List<Asignaturas> listar() throws Exception;

	public Asignaturas buscarPorId(Integer id) throws Exception;

	public List<Asignaturas> eliminar() throws Exception;

	public Asignaturas borrarPorId(Integer id) throws Exception;

	public Asignaturas actualizar(Asignaturas asig) throws Exception;

	public Asignaturas crear(Asignaturas asig) throws Exception;
}
