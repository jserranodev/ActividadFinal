package com.nttdata.activididadfinal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nttdata.activididadfinal.repository.entity.Usuario;
import com.nttdata.activididadfinal.service.AsignaturasService;
import com.nttdata.activididadfinal.service.UsuarioService;

@Controller
public class ActFinalWebController {

	@Autowired
	public AsignaturasService asignaturasService;
	@Autowired
	public UsuarioService usuarioService;

	@GetMapping("/")
	public String index(Model model) {
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("usuario", usuario);
		return "index";
	}
	
	@GetMapping("/asignaturas")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String listar(Model model) {
		model.addAttribute("listaAsignaturas", asignaturasService.listar());
		return "asignaturas.html";
	}

	@GetMapping("/usuarios-consulta")
	public String listaUsuariosConsulta(Model model) {
		model.addAttribute("listaUsuariosConsulta", usuarioService.listarFiltrarPorRol("CONSULTA"));
		return "usuariosConsulta";
	}

}
