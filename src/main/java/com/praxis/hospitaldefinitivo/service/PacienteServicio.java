package com.praxis.hospitaldefinitivo.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import com.praxis.hospitaldefinitivo.model.dao.PacienteDao;
import com.praxis.hospitaldefinitivo.model.dto.Paciente;

@Service
public class PacienteServicio {

    private static final Logger logger = LoggerFactory.getLogger(PacienteServicio.class);
	
	@Autowired
	private PacienteDao pacienteDao = new PacienteDao();

	private List<Paciente> pacientes = new ArrayList<Paciente>();
	
	//Creando pacientes con información falsa
	public String crearPacientes() {
		logger.info("Creando pacientes");
		String resultado = pacienteDao.createPacientes();
		logger.info("Pacientes creados exitosamente");
		return resultado;
	}
	
	//Método FindAll
	
	public List<Paciente> listarPacientes(){
		logger.info("Listando pacientes");
		List<Paciente> pacientes = pacienteDao.findAll();
		logger.info("Pacientes listados exitosamente");
		return pacientes;
	}
	
	//Método findById
	public Optional<Paciente> buscarPacientePorId(Integer id){
		logger.info("Buscando paciente con ID: {}", id);
		Optional<Paciente> resultado = pacienteDao.findById(id);
		if (resultado.isPresent()) {
			logger.info("Paciente encontrado: {}",  resultado.get());
		} else {
			logger.warn("Paciente con ID {} no encontrado", id);
		}
		return resultado;
	}
	
	//Método crear y actualizar pacientes
	public Paciente guardarPaciente(Paciente paciente) {
		pacienteDao.save(paciente);
		logger.info("Guardando paciente: {}", paciente);
		Paciente pacienteGuardado = pacienteDao.save(paciente);
		logger.info("Paciente guardado: {}", pacienteGuardado);
		return pacienteGuardado;
	}
	
	
	//Delete by Id
	public String eliminarPacientePorId(Integer id) {
		pacienteDao.deleteById(id);
		logger.info("Eliminando paciente con ID: {}", id);
		String resultado = pacienteDao.deleteById(id);
		logger.info("Resultado de la eliminación: {}", resultado);
		return resultado;
	}
    

    

    

    

}
