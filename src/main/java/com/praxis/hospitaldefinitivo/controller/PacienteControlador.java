package com.praxis.hospitaldefinitivo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.praxis.hospitaldefinitivo.model.dto.Paciente;
import com.praxis.hospitaldefinitivo.service.PacienteServicio;

@Controller
public class PacienteControlador {

    @Autowired
	private PacienteServicio pacienteServicio;
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String init(Model model) {
		pacienteServicio.crearPacientes();
		List<Paciente> pacientes = pacienteServicio.listarPacientes();
		model.addAttribute("pacientes", pacientes);
		return "pacientesIndex";
	}
	
	@RequestMapping(value = "/filtrarPacientes", method=RequestMethod.POST)
	public ModelAndView filtrarPacientes(@RequestParam String txtIdentificador) {
		System.out.println(txtIdentificador);
		ModelAndView modelAndView = new ModelAndView();

		if(txtIdentificador == null || txtIdentificador.trim().isEmpty()) {
			modelAndView.addObject("mensaje", "El identificador no puede ser vacío");
			List<Paciente> pacientes = pacienteServicio.listarPacientes();
			modelAndView.addObject("pacientes", pacientes);
			modelAndView.setViewName("pacientesIndex");
		} else {
			try{
				Integer id = Integer.parseInt(txtIdentificador);

				if (id <= 0){
					List<Paciente> pacientes = pacienteServicio.listarPacientes();
					modelAndView.addObject("pacientes", pacientes);
					modelAndView.setViewName("pacientesIndex");
				} else {
					Optional<Paciente> paciente = pacienteServicio.buscarPacientePorId(id);					

					if(paciente.isPresent()) {
						List<Paciente> pacientesFiltrados = new ArrayList<>();
						pacientesFiltrados.add(paciente.get());
						modelAndView.addObject("pacientes", pacientesFiltrados);
					} else {
						modelAndView.addObject("mensaje", "Paciente no encontrado");
						List<Paciente> pacientes = pacienteServicio.listarPacientes();
						modelAndView.addObject("pacientes", pacientes);
						modelAndView.setViewName("pacientesIndex");
					}
				}
			} catch (NumberFormatException  e) {
				modelAndView.addObject("mensaje", "El identificador debe ser un número válido");
			}
		}
		modelAndView.setViewName("pacientesIndex");
		return modelAndView;
	}

	@RequestMapping(value = "/preActualizarPaciente", method = RequestMethod.GET)
	public ModelAndView preActualizarPaciente(@RequestParam String txtIdentificador){
		ModelAndView modelAndView = new ModelAndView();
Integer id = Integer.parseInt(txtIdentificador);
Paciente pacienteBuscado = pacienteServicio.buscarPacientePorId(id).get();
		modelAndView.addObject("paciente", pacienteBuscado);
		modelAndView.setViewName("actualizarPaciente");
		return modelAndView;
	}

	@RequestMapping(value = "/actualizar", method = RequestMethod.POST)
	public ModelAndView actualizarPaciente(@RequestParam Map<String, String> parametros, Model model){
		ModelAndView modelAndView = new ModelAndView();
		String.format("%s %s %s %s %s %s %s", parametros.get("txtNombre"), parametros.get("txtApellidos"), parametros.get("txtRut"), parametros.get("txtEdad"), parametros.get("txtDiagnostico"), parametros.get("txtSalaHospitalizacion"), parametros.get("txtIdentificador"));

		Paciente pacienteActualizado = new Paciente();
		Integer edad = Integer.parseInt(parametros.get("txtEdad"));
		Integer id = Integer.parseInt(parametros.get("txtIdentificador"));

		pacienteActualizado.setId(id);
		pacienteActualizado.setNombre(parametros.get("txtNombre"));
		pacienteActualizado.setApellido(parametros.get("txtApellidos"));
		pacienteActualizado.setRut(parametros.get("txtRut"));
		pacienteActualizado.setEdad(edad);
		pacienteActualizado.setEnfermedad(parametros.get("txtDiagnostico"));
		pacienteActualizado.setSalaHospitalizacion(parametros.get("txtSalaHospitalizacion"));
		pacienteActualizado.setFechaIngreso(new Date());

		if (pacienteActualizado.getId() == 0){
			List<Paciente> pacientes = pacienteServicio.listarPacientes();
			modelAndView.addObject("mensaje", "No se puede agregar al paciente debido a que no se encuentra su id");
			System.out.println("No se puede agregar al paciente debido a que no se encuentra su id");
			modelAndView.addObject("pacientes", pacientes);
			modelAndView.setViewName("pacientesIndex");
			return modelAndView;
		}

		pacienteServicio.guardarPaciente(pacienteActualizado);
		modelAndView.addObject("mensaje", "Paciente actualizado con exito");
		List<Paciente> pacientes = pacienteServicio.listarPacientes();
		modelAndView.addObject("pacientes", pacientes);
		modelAndView.setViewName("pacientesIndex");
        return modelAndView;
	}

	@RequestMapping(value="/eliminarPaciente", method = RequestMethod.GET)
	public ModelAndView eliminarPaciente(@RequestParam String txtIdentificador){
		ModelAndView modelAndView = new ModelAndView();
		pacienteServicio.eliminarPacientePorId(Integer.parseInt(txtIdentificador));
		modelAndView.addObject("mensaje", "Paciente eliminado");
		List<Paciente> pacientes = pacienteServicio.listarPacientes();
		modelAndView.addObject("pacientes", pacientes);
		modelAndView.setViewName("pacientesIndex");
		return modelAndView;
	}

}
