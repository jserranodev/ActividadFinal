package com.nttdata.activididadfinal.repository.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Objects;

import org.junit.jupiter.api.Test;

class AsignaturasTest {

	@Test
	void test() {
		Asignaturas asig1 = new Asignaturas();

		asig1.setId(1);
		assertEquals(1, asig1.getId(), "Mismo id");

		String nombre = "Prog. interfaces";
		asig1.setNombre(nombre);
		assertEquals(nombre, asig1.getNombre(), "Mismo nombre");

		String descripcion = "Programación interfaces gráficas";
		asig1.setDescripcion(descripcion);
		assertEquals(descripcion, asig1.getDescripcion(), "Misma descripción");

		asig1.setCurso(2);
		assertEquals(2, asig1.getCurso(), "Mismo curso");

		Asignaturas asig2 = new Asignaturas();
		asig2.setId(1);
		asig2.setNombre(nombre);
		asig2.setDescripcion(descripcion);
		asig2.setCurso(2);
		assertEquals(asig1, asig2, "Mismas asignaturas");

		assertEquals(Objects.hash(asig1.getId()), asig2.hashCode(), "Mismo hash");
	}

}
