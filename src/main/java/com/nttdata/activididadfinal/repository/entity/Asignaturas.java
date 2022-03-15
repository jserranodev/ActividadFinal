package com.nttdata.activididadfinal.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Asignaturas {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer id;
	
	@Column(nullable=false,length=20)
	private String nombre;
	
	@Column(nullable=false,length=50)
	private String descripcion;
	
	@Column(nullable=false)
	private Integer curso;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getCurso() {
		return curso;
	}

	public void setCurso(Integer curso) {
		this.curso = curso;
	}

	@Override
	public String toString() {
		return "Asignaturas [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", curso=" + curso
				+ "]";
	}
	
}
