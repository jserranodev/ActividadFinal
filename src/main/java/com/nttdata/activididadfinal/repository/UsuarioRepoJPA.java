package com.nttdata.activididadfinal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nttdata.activididadfinal.repository.entity.Usuario;

public interface UsuarioRepoJPA extends JpaRepository<Usuario, String>, UsuarioRepo {

//	@Query(value = "select * from usuario where rol_id=(select id from rol where rol=?1)", nativeQuery = true)
//	public List<Usuario> listarFiltrarPorRol(String rol);
	@Query(value="select * from usuario", nativeQuery=true)
	public List<Usuario> listarUsuarios();
}
