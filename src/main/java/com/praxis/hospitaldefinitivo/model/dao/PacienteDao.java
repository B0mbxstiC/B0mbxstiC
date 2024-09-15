package com.praxis.hospitaldefinitivo.model.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.praxis.hospitaldefinitivo.model.dto.Paciente;

@Repository
public class PacienteDao {

    private List<Paciente> pacientes = new ArrayList<Paciente>();
	private Integer idPaciente = 1;
	
	//Crear pacientes ficticios
	
	public String createPacientes() {
		//Creando pacientes con información falsa
		boolean estado = false;
		String exito = "";
		String error = "";

		if (pacientes.isEmpty()) {
			do {
				pacientes.add(new Paciente(idPaciente++, "Juan", "Pérez", "12345678-9", 30, "COVID-19", "Sala 1", new Date()));
				pacientes.add(new Paciente(idPaciente++, "María", "López", "98765432-1", 45, "Neumonía", "Sala 2", new Date()));
				pacientes.add(new Paciente(idPaciente++, "Carlos", "Gómez", "19283746-5", 60, "Bronquitis", "Sala 3", new Date()));
				pacientes.add(new Paciente(idPaciente++, "Ana", "Martínez", "56473829-4", 55, "Diabetes", "Sala 4", new Date()));
				pacientes.add(new Paciente(idPaciente++, "Luis", "Fernández", "37482916-7", 40, "Hipertensión", "Sala 5", new Date()));
				for(Paciente paciente : pacientes) {
					if (paciente.getId().equals(idPaciente)) {
						exito = "Los pacientes fueron agregados con éxito";
					}
				}
				error = "Los pacientes no fueron agregados";
				estado = true;

			} while(!estado);
			return exito;
		} else {
			return error;
		}

		}
		
	//FindAll
	public List<Paciente> findAll(){
		System.out.println(pacientes);
		return new ArrayList<Paciente>(pacientes);
	}
	
	//FindByID
	
	public Optional<Paciente> findById(Integer id) {
		for(Paciente pacienteTemporal : pacientes) {
			if (pacienteTemporal.getId().equals(id)) {
				//Optional.of indica que el valor presente es no nulo
				return Optional.of(pacienteTemporal);
			}
		}
		//Optional.empty indica que no existe un paciente con el id buscado en la lista
		return Optional.empty();
	}
	
	
	//Crear y actualizar pacientes: GuardarPacientes
	
	public Paciente save(Paciente paciente) {
		if (paciente.getId() == 0) {
			paciente.setId(idPaciente++);
			pacientes.add(paciente);
		} else {
			for(int i = 0; i < pacientes.size(); i++) {
				if (pacientes.get(i).getId() == paciente.getId()) {
					pacientes.set(i, paciente);
					return paciente;
				}
			}
			pacientes.add(paciente);
		}
		return paciente;
	}
	
	//DeleteById
	public String deleteById(Integer id) {
		String error = "";
		String exito = "";
		for(int i = 0; i < pacientes.size(); i++) {
			if (pacientes.get(i).getId() == id) {
				pacientes.remove(i);
				exito = "El paciente fue eliminado exitosamente";
				System.out.println(exito);
				return exito;
			}
		}
		error = "Hubo un error al eliminar al paciente";
		System.out.println(error);
		return error;
	}

    

}
