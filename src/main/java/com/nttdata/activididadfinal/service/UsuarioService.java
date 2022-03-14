package com.nttdata.activididadfinal.service;

import java.util.List;

import com.nttdata.activididadfinal.repository.entity.Rol;
import com.nttdata.activididadfinal.repository.entity.Usuario;

public interface UsuarioService {
	public Usuario buscarPorUsername(String username);
	public List<Usuario> listarFiltrarPorRol(String rol);
	public List<Usuario> listarUsuarios();
}
