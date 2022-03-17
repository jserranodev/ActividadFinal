package com.nttdata.activididadfinal.controller.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import com.nttdata.activididadfinal.repository.AsignaturasRepoJPA;
import com.nttdata.activididadfinal.repository.entity.Asignaturas;
import com.nttdata.activididadfinal.service.AsignaturasService;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class AsignaturasControllerTest {

	private Asignaturas asig1, asig2;

	@Autowired
	AsignaturasController asignaturasController;

	@Autowired
	AsignaturasService asignaturasService;

	@Autowired
	AsignaturasRepoJPA asignaturasRepoJPA;

	@InjectMocks
	AsignaturasController asignaturasControllerMock;

	@Mock
	AsignaturasService asigServiceMock;

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
	void testConsultarTodos() throws Exception {
		// GIVEN:
		// -- Hay dos asignaturas en la BBDD.

		// WHEN:
		ResponseEntity<List<Asignaturas>> re = asignaturasController.consultarTodos();

		// THEN:
		assertAll(() -> assertEquals(2, re.getBody().size(), "Hay dos asignaturas en BBDD"),
				() -> assertEquals(re.getStatusCode(), HttpStatus.OK, "Código respuesta 200 OK"));
	}

	@Test
	void testConsultarTodosException() throws Exception {
		// GIVEN:
		// -- Hay dos asignaturas en la BBDD.
		when(asigServiceMock.listar()).thenThrow(new Exception());

		// WHEN:
		ResponseEntity<List<Asignaturas>> re = asignaturasControllerMock.consultarTodos();

		// THEN:
		assertAll(() -> assertEquals(500, re.getStatusCodeValue(), "Código respuesta 500"),
				() -> assertThat(re.getStatusCodeValue()).isEqualTo(500));

	}

	@Test
	void testConsultarPorId() throws Exception {
		// GIVEN:
		// -- Hay dos asignaturas en la BBDD.

		// WHEN:
		ResponseEntity<Asignaturas> re = asignaturasController.consultarPorId(asig2.getId());
		ResponseEntity<Asignaturas> reNotFound = asignaturasController.consultarPorId(5);

		// THEN:
		assertAll(
				// Camino OK
				() -> assertNotNull(re.getBody(), "Encontrada asignatura con id:" + re.getBody().getId()),
				() -> assertEquals(re.getBody(), asig2, "Encontrada asignatura con id:" + asig2.getId()),
				() -> assertEquals(re.getStatusCode(), HttpStatus.OK, "Código respuesta 200 OK"),
				// Camino NOT FOUND
				() -> assertEquals(reNotFound.getStatusCode(), HttpStatus.NOT_FOUND, "Código respuesta 404 NOT FOUND"));
	}

	@Test
	void testConsultarPorIdException() throws Exception {
		// GIVEN:
		// -- Hay dos asignaturas en la BBDD.
		when(asigServiceMock.buscarPorId(asig2.getId())).thenThrow(new Exception());

		// WHEN:
		ResponseEntity<Asignaturas> re = asignaturasControllerMock.consultarPorId(asig2.getId());

		// THEN:
		assertEquals(500, re.getStatusCodeValue(), "Código respuesta 500");
	}

	@Test
	void testEliminarTodos() {
		// GIVEN:
		// -- Hay dos asignaturas en la BBDD.

		// WHEN:
		ResponseEntity<List<Asignaturas>> re = asignaturasController.eliminarTodos();

		// THEN:
		assertAll(() -> assertNull(re.getBody(), "Cuerpo respuesta nulo"),
				() -> assertEquals(re.getStatusCode(), HttpStatus.OK, "Código respuesta 200 OK"));
	}

	@Test
	void testEliminarTodosException() throws Exception {
		// GIVEN:
		// -- Hay dos asignaturas en la BBDD.
		when(asigServiceMock.eliminar()).thenThrow(new Exception());

		// WHEN:
		ResponseEntity<List<Asignaturas>> re = asignaturasControllerMock.eliminarTodos();

		// THEN:
		assertAll(() -> assertNull(re.getBody(), "Cuerpo respuesta nulo"), () -> assertEquals(re.getStatusCode(),
				HttpStatus.INTERNAL_SERVER_ERROR, "Código respuesta 500 INTERNAL SERVER ERROR"));
	}

	@Test
	void testEliminarPorId() {
		// GIVEN:
		// -- Hay dos asignaturas en la BBDD.

		// WHEN:
		ResponseEntity<Asignaturas> re = asignaturasController.eliminarPorId(asig2.getId());
		ResponseEntity<Asignaturas> reNotFound = asignaturasController.eliminarPorId(5);

		// THEN:
		assertAll(// Camino OK
				() -> assertNotNull(re.getBody(), "Eliminada asignatura con id:" + re.getBody().getId()),
				() -> assertThat(re.getBody()).isEqualTo(asig2),
				() -> assertEquals(HttpStatus.OK, re.getStatusCode(), "Código respuesta 200 OK"),
				// Camino NOT FOUND
				() -> assertThat(reNotFound.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));
	}

	@Test
	void testEliminarPorIdException() throws Exception {
		// GIVEN:
		// -- Hay dos asignaturas en la BBDD.
		when(asigServiceMock.borrarPorId(asig2.getId())).thenThrow(new Exception());

		// WHEN:
		ResponseEntity<Asignaturas> re = asignaturasControllerMock.eliminarPorId(asig2.getId());

		// THEN:
		assertThat(re.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Test
	void testModificar() {
		// GIVEN:
		// -- Hay dos asignaturas en la BBDD.

		// WHEN:
		Integer idCopy = asig1.getId();
		asig1.setId(null);
		ResponseEntity<Asignaturas> re = asignaturasController.modificar(asig1);

		asig1.setId(idCopy);
		asig1.setNombre(null);
		asig1.setDescripcion(null);
		asig1.setCurso(null);
		ResponseEntity<Asignaturas> re2 = asignaturasController.modificar(asig1);

		idCopy = asig2.getId();
		asig2.setId(333333);
		ResponseEntity<Asignaturas> re3 = asignaturasController.modificar(asig2);

		asig2.setId(idCopy);
		asig2.setNombre("PSP");
		ResponseEntity<Asignaturas> re4 = asignaturasController.modificar(asig2);

		// THEN:
		assertAll(// Camino if(asig.getId()==null)
				() -> assertNull(re.getBody(), "Se necesita campo ID para modificar una asignatura"),
				() -> assertEquals("Para modificar una asignatura es necesario su ID.",
						re.getHeaders().get("Message").get(0), "Contenido cabecera 'Message' con ID nulo, correcto"),
				() -> assertEquals(HttpStatus.NOT_ACCEPTABLE, re.getStatusCode(),
						"Código de respuesta 406 NOT ACCEPTABLE"),
				// Camino if (asig.getNombre() == null && asig.getDescripcion() == null &&
				// asig.getCurso() == null)
				() -> assertNull(re2.getBody(),
						"Para modificar una asignatura se necesita algún campo más aparte del ID"),
				() -> assertEquals("Para modificar una asignatura es necesario algún campo además del ID.",
						re2.getHeaders().get("Message").get(0),
						"Contenido cabecera 'Message' con campos nulos menos ID, correcto"),
				() -> assertEquals(HttpStatus.NOT_ACCEPTABLE, re2.getStatusCode(),
						"Código de respuesta 406 NOT ACCEPTABLE"),
				// Camino if(asignaturaModificada == null)
				() -> assertEquals(HttpStatus.NOT_FOUND, re3.getStatusCode(), "Código de respuesta 404 NOT FOUND"),
				// Camino todo OK
				() -> assertNotNull(re4.getBody(), "Asignatura modificada correctamente con id: " + asig2.getId()),
				() -> assertEquals("Asignatura actualizada correctamente  con id:" + asig2.getId(),
						re4.getHeaders().get("Message").get(0),
						"Contenido cabecera 'Message' modificación exitosa, correcto"),
				() -> assertEquals(HttpStatus.OK, re4.getStatusCode(), "Código de respuesta 200 OK"));
	}

	@Test
	void testModificarException() throws Exception {
		// GIVEN:
		// -- Hay dos asignaturas en la BBDD.
		when(asigServiceMock.actualizar(asig2)).thenThrow(new Exception());

		// WHEN:
		ResponseEntity<Asignaturas> re = asignaturasControllerMock.modificar(asig2);

		// THEN:
		assertThat(re.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Test
	void testInsertar() {
		// GIVEN:
		// -- Hay dos asignaturas en la BBDD.

		// WHEN:
		ResponseEntity<Asignaturas> re = asignaturasController.insertar(asig1);

		asig1.setId(null);
		asig1.setNombre(null);
		ResponseEntity<Asignaturas> re2 = asignaturasController.insertar(asig1);

		asig1.setNombre("Nuevo nombre");
		asig1.setDescripcion(null);
		ResponseEntity<Asignaturas> re3 = asignaturasController.insertar(asig1);

		asig1.setDescripcion("Nueva descripción");
		asig1.setCurso(null);
		ResponseEntity<Asignaturas> re4 = asignaturasController.insertar(asig1);

		asig2.setId(null);
		ResponseEntity<Asignaturas> re5 = asignaturasController.insertar(asig2);

		// THEN:
		assertAll(
				// Camino if(asig.getId() != null)
				() -> assertNull(re.getBody(), "Se necesita campo ID nulo para insertar una asignatura"),
				() -> assertEquals("Para dar de alta una asignatura, el ID debe llegar vacío.",
						re.getHeaders().get("Message").get(0), "Contenido cabecera 'Message' con ID NO nulo, correcto"),
				() -> assertEquals(HttpStatus.NOT_ACCEPTABLE, re.getStatusCode(),
						"Código de respuesta 406 NOT ACCEPTABLE"),
				// Camino if(asig.getNombre() == null ... )
				() -> assertNull(re2.getBody(), "El campo 'nombre' es nulo"),
				() -> assertEquals("Los campos 'nombre', 'descripcion' y 'curso' no pueden ser nulos.",
						re2.getHeaders().get("Message").get(0),
						"Contenido cabecera 'Message' con NOMBRE nulo, correcto"),
				() -> assertEquals(HttpStatus.NOT_ACCEPTABLE, re2.getStatusCode(),
						"Código de respuesta 406 NOT ACCEPTABLE"),
				// Camino if( ... asig.getDescripcion() == null ... )
				() -> assertNull(re3.getBody(), "El campo 'descripcion' es nulo"),
				() -> assertEquals("Los campos 'nombre', 'descripcion' y 'curso' no pueden ser nulos.",
						re3.getHeaders().get("Message").get(0),
						"Contenido cabecera 'Message' con DESCRIPCION nulo, correcto"),
				() -> assertEquals(HttpStatus.NOT_ACCEPTABLE, re3.getStatusCode(),
						"Código de respuesta 406 NOT ACCEPTABLE"),
				// Camino if( ... asig.getCurso() == null )
				() -> assertNull(re4.getBody(), "El campo 'descripcion' es nulo"),
				() -> assertEquals("Los campos 'nombre', 'descripcion' y 'curso' no pueden ser nulos.",
						re4.getHeaders().get("Message").get(0),
						"Contenido cabecera 'Message' con CURSO nulo, correcto"),
				() -> assertEquals(HttpStatus.NOT_ACCEPTABLE, re4.getStatusCode(),
						"Código de respuesta 406 NOT ACCEPTABLE"),
				// Camino todo OK
				() -> assertNotNull(re5.getBody(), "Cuerpo respuesta devuelta NO nulo"),
				() -> assertEquals("Asignatura insertada correctamente con id:" + re5.getBody().getId(),
						re5.getHeaders().get("Message").get(0),
						"Contenido cabecera 'Message' tras insertar asignatura, correcto"),
				() -> assertTrue(
						re5.getHeaders().getLocation().getPath().contains("/api/asignaturas/" + re5.getBody().getId())),
				() -> assertEquals(HttpStatus.CREATED, re5.getStatusCode(), "Código de respuesta 201 CREATED")

		);

	}

	@Test
	void testInsertarException() throws Exception {
		// GIVEN:
		// -- Hay dos asignaturas en la BBDD.
		when(asigServiceMock.crear(asig2)).thenThrow(new Exception());

		// WHEN:
		asig2.setId(null);
		ResponseEntity<Asignaturas> re = asignaturasControllerMock.insertar(asig2);

		// THEN:
		assertThat(re.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
