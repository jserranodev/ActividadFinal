package com.nttdata.activididadfinal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.activididadfinal.repository.AsignaturasRepoJPA;
import com.nttdata.activididadfinal.repository.entity.Asignaturas;
import com.nttdata.activididadfinal.service.AsignaturasService;

@Service
public class AsignaturasServiceImpl implements AsignaturasService {

	@Autowired
	AsignaturasRepoJPA asignaturasRepoJPA;

	@Override
	public List<Asignaturas> listar() throws Exception {
		return asignaturasRepoJPA.findAll();
	}

	@Override
	public Asignaturas buscarPorId(Integer id) throws Exception {
		return asignaturasRepoJPA.findById(id).orElse(null);
	}

	@Override
	public List<Asignaturas> eliminar() throws Exception {
		asignaturasRepoJPA.deleteAll();
		return listar();
	}

	@Override
	public Asignaturas borrarPorId(Integer id) throws Exception {
		Asignaturas asig = this.buscarPorId(id);
		if (asig != null)
			asignaturasRepoJPA.deleteById(id);
		return asig;
	}

	@Override
	public Asignaturas actualizar(Asignaturas asig) throws Exception {
		Asignaturas asigEnBbdd = this.buscarPorId(asig.getId());
		if (asigEnBbdd == null) {
			return null;
		} else {
			asig.setNombre(asig.getNombre() == null ? asigEnBbdd.getNombre() : asig.getNombre());
			asig.setDescripcion(asig.getDescripcion() == null ? asigEnBbdd.getDescripcion() : asig.getDescripcion());
			asig.setCurso(asig.getCurso() == null ? asigEnBbdd.getCurso() : asig.getCurso());
			asignaturasRepoJPA.save(asig);
			return asig;
		}
	}

	@Override
	public Asignaturas crear(Asignaturas asig) throws Exception{
		System.out.println(asig.toString());
		return asignaturasRepoJPA.save(asig);
	}

}
