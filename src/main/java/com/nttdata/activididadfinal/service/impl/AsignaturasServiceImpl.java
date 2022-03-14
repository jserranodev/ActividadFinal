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
	public List<Asignaturas> listar() {
		// TODO Auto-generated method stub
		return asignaturasRepoJPA.findAll();
	}

}
