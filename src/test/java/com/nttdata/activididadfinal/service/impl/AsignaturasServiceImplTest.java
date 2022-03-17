package com.nttdata.activididadfinal.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.nttdata.activididadfinal.repository.AsignaturasRepoJPA;
import com.nttdata.activididadfinal.repository.entity.Asignaturas;
import com.nttdata.activididadfinal.service.AsignaturasService;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class AsignaturasServiceImplTest {

	private Asignaturas asig1, asig2;

	@Autowired
	AsignaturasRepoJPA asignaturasRepoJPA;
	@Autowired
	AsignaturasService asignaturasService;

	@BeforeEach
	void setUp() throws Exception {
		asignaturasRepoJPA.deleteAll();

		asig1 = new Asignaturas();
		asig1.setNombre("Prog. interfaces");
		asig1.setDescripcion("Programación interfaces gráficas");
		asig1.setCurso(2);
		asig1 = asignaturasRepoJPA.save(asig1);

		asig2 = new Asignaturas();
		asig2.setNombre("Servicios y procesos");
		asig2.setDescripcion("Programación de servicios y procesos");
		asig2.setCurso(2);
		asig2 = asignaturasRepoJPA.save(asig2);
	}

	@AfterEach
	void tearDown() throws Exception {
		asignaturasRepoJPA.deleteAll();
	}

	@Test
	void testListar() throws Exception {
		// GIVEN:
		// -- Hay dos asignaturas en la BBDD.

		// WHEN:
		List<Asignaturas> listAsignatuas = asignaturasService.listar();

		// THEN:
		assertEquals(2, listAsignatuas.size(), "Hay dos asignaturas en BBDD");
	}

	@Test
	void testBuscarPorId() throws Exception {
		// GIVEN:
		// -- Hay dos asignaturas en la BBDD.

		// WHEN:
		Asignaturas asig3 = asignaturasService.buscarPorId(asig2.getId());

		// THEN:
		assertNotNull(asig3, "Encontrada asignaturas con id: " + asig2.getId());
	}

	@Test
	void testEliminar() throws Exception {
		// GIVEN:
		assertEquals(2, asignaturasRepoJPA.findAll().size(), "Hay dos asignaturas en BBDD");

		// WHEN:
		asignaturasService.eliminar();

		// THEN:
		assertEquals(0, asignaturasRepoJPA.findAll().size(), "No queda ninguna asignatura en BBDD");
	}

	@Test
	void testBorrarPorId() throws Exception {
		// GIVEN:
		assertEquals(2, asignaturasRepoJPA.findAll().size(), "Hay dos asignaturas en BBDD");

		// WHEN:
		Asignaturas asig3 = asignaturasService.borrarPorId(asig2.getId());

		// THEN:
		assertEquals(1, asignaturasRepoJPA.findAll().size(), "Ahora hay una asignatura en BBDD");
		assertNull(asignaturasService.buscarPorId(asig3.getId()),
				"Borrada correctamente asinatura con id: " + asig2.getId());
	}

	@Test
	void testActualizar() throws Exception {
		// GIVEN:
		assertEquals(2, asignaturasRepoJPA.findAll().size(), "Hay dos asignaturas en BBDD");

		String nuevoNombre = "Nuevo nombre";
		asig2.setNombre(nuevoNombre);
		Asignaturas asig3 = asignaturasService.actualizar(asig2);

		// THEN:
		assertEquals(2, asignaturasRepoJPA.findAll().size(), "Sigue habiendo dos asignaturas en BBDD");
		assertEquals(nuevoNombre, asig3.getNombre(),
				"Ha sido actualizado el nombre de la asignatura con id: " + asig2.getId());
	}

	@Test
	void testCrear() throws Exception{
		// GIVEN:
		assertEquals(2, asignaturasRepoJPA.findAll().size(), "Hay dos asignaturas en BBDD");

		// WHEN:
		Asignaturas asig3 = new Asignaturas();
		asig3.setNombre("Lenguajes de marcas");
		asig3.setDescripcion("Conceptos básicos lenguajes de marcas");
		asig3.setCurso(1);
		Asignaturas asig4 = asignaturasService.crear(asig3);

		// THEN:
		assertEquals(3, asignaturasRepoJPA.findAll().size(), "Ahora hay tres asignaturas en BBDD");
		assertEquals(asig4, asig3, "Ha sido insertada la asignatura con id: " + asig3.getId());
	}

}
