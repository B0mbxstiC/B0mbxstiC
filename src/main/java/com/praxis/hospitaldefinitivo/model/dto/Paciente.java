package com.praxis.hospitaldefinitivo.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Paciente {

    private Integer id;
	private String nombre;
	private String apellido;
	private String rut;
	private Integer edad;
	private String enfermedad;	
	private String salaHospitalizacion;
	private Date fechaIngreso;    

}
