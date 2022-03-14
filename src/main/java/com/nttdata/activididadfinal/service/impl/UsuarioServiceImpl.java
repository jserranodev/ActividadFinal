package com.nttdata.activididadfinal.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nttdata.activididadfinal.repository.UsuarioRepoJPA;
import com.nttdata.activididadfinal.repository.entity.Usuario;
import com.nttdata.activididadfinal.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService{

	@Autowired
	UsuarioRepoJPA usuarioDAO;
	
	@Override
	public Usuario buscarPorUsername(String username) {
		// TODO Auto-generated method stub
		return usuarioDAO.findById(username).get();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return buscarPorUsername(username);
	}

	@Override
	public List<Usuario> listarFiltrarPorRol(String rol) {
		// TODO Auto-generated method stub
		return usuarioDAO.listarFiltrarPorRol(rol);
		}

	@Override
	public List<Usuario> listarUsuarios() {
		// TODO Auto-generated method stub
		return usuarioDAO.listarUsuarios();
	}

}
