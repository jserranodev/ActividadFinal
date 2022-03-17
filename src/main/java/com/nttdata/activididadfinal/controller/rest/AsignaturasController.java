package com.nttdata.activididadfinal.controller.rest;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.activididadfinal.repository.entity.Asignaturas;
import com.nttdata.activididadfinal.service.AsignaturasService;

@RestController
@RequestMapping("/api/asignaturas")
public class AsignaturasController {

	@Autowired
	private AsignaturasService asignaturasService;

	@GetMapping()
	@Cacheable(value = "asignaturas")
	public ResponseEntity<List<Asignaturas>> consultarTodos() {
		try {
			return new ResponseEntity<>(asignaturasService.listar(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Asignaturas> consultarPorId(@PathVariable("id") Integer id) {
		try {
			Asignaturas asignaturaConsultada = asignaturasService.buscarPorId(id);
			if (asignaturaConsultada == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(asignaturaConsultada, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping()
	@CacheEvict(value = "asignaturas", allEntries = true)
	public ResponseEntity<List<Asignaturas>> eliminarTodos() {
		try {
			asignaturasService.eliminar();
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{id}")
	@CacheEvict(value = "asignaturas", allEntries = true)
	public ResponseEntity<Asignaturas> eliminarPorId(@PathVariable("id") Integer id) {
		try {
			Asignaturas asignaturaBorrada = asignaturasService.borrarPorId(id);
			if (asignaturaBorrada == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(asignaturaBorrada, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping()
	@CacheEvict(value = "asignaturas", allEntries = true)
	public ResponseEntity<Asignaturas> modificar(@RequestBody Asignaturas asig) {
		try {
			HttpHeaders headers = new HttpHeaders();

			if (asig.getId() == null) {
				headers.set("Message", "Para modificar una asignatura es necesario su ID.");
				return new ResponseEntity<>(headers, HttpStatus.NOT_ACCEPTABLE);
			} else if (asig.getNombre() == null && asig.getDescripcion() == null && asig.getCurso() == null) {
				headers.set("Message", "Para modificar una asignatura es necesario algún campo además del ID.");
				return new ResponseEntity<>(headers, HttpStatus.NOT_ACCEPTABLE);
			}

			Asignaturas asignaturaModificada = asignaturasService.actualizar(asig);
			if (asignaturaModificada == null) {
				return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
			}

			headers.set("Message", "Asignatura actualizada correctamente  con id:" + asignaturaModificada.getId());
			return new ResponseEntity<>(asignaturaModificada, headers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping()
	@CacheEvict(value = "asignaturas", allEntries = true)
	public ResponseEntity<Asignaturas> insertar(@RequestBody Asignaturas asig) {
		try {
			HttpHeaders headers = new HttpHeaders();

			if (asig.getId() != null) {
				headers.set("Message", "Para dar de alta una asignatura, el ID debe llegar vacío.");
				return new ResponseEntity<>(headers, HttpStatus.NOT_ACCEPTABLE);
			} else if (asig.getNombre() == null || asig.getDescripcion() == null || asig.getCurso() == null) {
				headers.set("Message", "Los campos 'nombre', 'descripcion' y 'curso' no pueden ser nulos.");
				return new ResponseEntity<>(headers, HttpStatus.NOT_ACCEPTABLE);
			}

			Asignaturas asignaturaCreada = asignaturasService.crear(asig);
			URI newPath = new URI("/api/asignaturas/" + asignaturaCreada.getId());
			headers.setLocation(newPath);
			headers.set("Message", "Asignatura insertada correctamente con id:" + asignaturaCreada.getId());
			return new ResponseEntity<>(asignaturaCreada, headers, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
