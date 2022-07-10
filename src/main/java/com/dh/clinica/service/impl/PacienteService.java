package com.dh.clinica.service.impl;

import com.dh.clinica.entities.Domicilio;
import com.dh.clinica.entities.Odontologo;
import com.dh.clinica.entities.Paciente;
import com.dh.clinica.exceptions.BadRequestException;
import com.dh.clinica.exceptions.ResourceNotFoundException;
import com.dh.clinica.repository.IPacienteRepository;
import com.dh.clinica.service.IService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PacienteService implements IService<Paciente> {

    private final Logger logger = Logger.getLogger(PacienteService.class);

    @Autowired
    private IPacienteRepository pacienteRepository;

    @Autowired
    DomicilioService domicilioService;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public Paciente guardar(Paciente paciente) throws BadRequestException {
        if(paciente != null){
            pacienteRepository.save(paciente);
            logger.info("Se registró exitosamente al paciente: " + paciente.getNombre() + " " + paciente.getApellido());
        } else {
            throw new BadRequestException("Los datos ingresados son incorrectos");
        }
        return paciente;
    }

    @Override
    public Paciente buscar(Long id) throws ResourceNotFoundException {
        Paciente paciente = pacienteRepository.findById(id).orElse(null);
        if (paciente != null){
            return paciente;
        } else {
            throw new ResourceNotFoundException ("No fue encontrado el odontologo con id " + id);
        }
    }

    @Override
    public Set<Paciente> buscarTodos() throws ResourceNotFoundException {
        List<Paciente> listaPacientes = pacienteRepository.findAll();
        Set<Paciente> pacientes = new HashSet<>();
        for (Paciente paciente : listaPacientes){
            pacientes.add(paciente);
        }
        logger.info("Se consultaron todos los pacientes de la base de datos");
        if (pacientes.size() == 0){
            throw new ResourceNotFoundException("No existen registros de pacientes aún");
        }
        return pacientes;
    }

    @Override
    public String eliminar(Long id) throws ResourceNotFoundException {
        String respuesta;
        if(pacienteRepository.findById(id).isPresent()){
            pacienteRepository.deleteById(id);
            respuesta = "Eliminado exitosamente";
        } else {
            throw new ResourceNotFoundException("No se logró eliminar el paciente. El id " + id + " no fue encontrado en la base de datos.");
        }
        return respuesta;
    }

    @Override
    public String actualizar(Paciente paciente) throws ResourceNotFoundException {
        String respuesta;
        Optional<Paciente> pacienteActualizar = pacienteRepository.findById(paciente.getId());
        if(pacienteActualizar.isPresent()){
            pacienteRepository.save(this.actualizarPaciente(pacienteActualizar.get(), paciente));
            respuesta = "Actualización exitosa del paciente con el siguiente id: " + paciente.getId();
        } else {
            throw new ResourceNotFoundException("No se pudo actualizar el paciente. El paciente con id " + paciente.getId() +" no fue encontrado en la base de datos");
        }
        return respuesta;
    }

    private Paciente actualizarPaciente(Paciente paciente, Paciente pacienteNuevo) throws ResourceNotFoundException{
        if (pacienteNuevo.getNombre() != null) {
            paciente.setNombre(pacienteNuevo.getNombre());
        }
        if (pacienteNuevo.getApellido() != null) {
            paciente.setApellido(pacienteNuevo.getApellido());
        }
        if (pacienteNuevo.getDni() != null) {
            paciente.setDni(pacienteNuevo.getDni());
        }
        if (pacienteNuevo.getDomicilio() != null) {
            Domicilio domicilioActualizado = domicilioService.actualizar(pacienteNuevo.getDomicilio());
            paciente.setDomicilio(domicilioActualizado);
        }

        return paciente;
    }

}