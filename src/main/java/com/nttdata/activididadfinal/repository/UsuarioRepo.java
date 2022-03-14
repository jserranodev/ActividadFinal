package com.nttdata.activididadfinal.repository;

import java.util.List;

import com.nttdata.activididadfinal.repository.entity.Usuario;

public interface UsuarioRepo {
	public List<Usuario> listarFiltrarPorRol(String rol);
}
