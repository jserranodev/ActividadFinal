package com.nttdata.activididadfinal.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.nttdata.activididadfinal.repository.UsuarioRepo;
import com.nttdata.activididadfinal.repository.entity.Usuario;

//@Repository
public class UsuarioRepoImpl implements UsuarioRepo {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Usuario> listarFiltrarPorRol(String rol) {
		Query query = entityManager.createNativeQuery(
				"select * from usuario where rol_id=(select id from rol where rol=?1)", Usuario.class);
		query.setParameter(1, rol);
		return query.getResultList();
	}

}
