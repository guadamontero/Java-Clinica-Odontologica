package com.dh.clinica.service.impl;


import com.dh.clinica.entities.Odontologo;
import com.dh.clinica.exceptions.BadRequestException;
import com.dh.clinica.exceptions.ResourceNotFoundException;
import com.dh.clinica.repository.IOdontologoRepository;
import com.dh.clinica.service.IService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OdontologoService implements IService<Odontologo> {

    @Autowired
    private IOdontologoRepository odontologoRepository;

    @Autowired
    private ObjectMapper mapper;

    public Odontologo guardar(Odontologo odontologo){
        return odontologoRepository.save(odontologo);
    }

    @Override
    public Odontologo buscar(Long id) throws ResourceNotFoundException{
        Odontologo odontologo = odontologoRepository.findById(id).orElse(null);
        if (odontologo != null){
            return odontologo;
        } else {
            throw new ResourceNotFoundException ("No fue encontrado el odontologo con id " + id);
        }
    }

    @Override
    public Set<Odontologo> buscarTodos() throws ResourceNotFoundException {
        List<Odontologo> listaOdontologos = odontologoRepository.findAll();
        Set<Odontologo> odontologos = new HashSet<>();
        for (Odontologo odontologo : listaOdontologos){
            odontologos.add(odontologo);
        }
        if (odontologos.size() == 0){
            throw new ResourceNotFoundException("No hay pacientes aún cargados en la base de datos");
        }
        return odontologos;
    }

    @Override
    public String eliminar(Long id) throws ResourceNotFoundException {
        String respuesta;
        if(odontologoRepository.findById(id).isPresent()){
            odontologoRepository.deleteById(id);
            respuesta = "Eliminado exitosamente";
        } else {
            throw new ResourceNotFoundException("No se logró eliminar el odontologo de la base de datos. El id " + id +" no fue encontrado.");
        }
        return respuesta;
    }

    @Override
    public String actualizar(Odontologo odontologo) throws ResourceNotFoundException {
        String respuesta;
        Optional<Odontologo> odontologoActualizar = odontologoRepository.findById(odontologo.getId());
        if(odontologoActualizar.isPresent()){
            odontologoRepository.save(this.actualizarOdontologo(odontologoActualizar.get(), odontologo));
            respuesta = "Actualización con éxito del odontólogo con id " + odontologo.getId();
        }else {
            throw new ResourceNotFoundException("No se logró actualizar el odontólogo. El odontólogo con id " + odontologo.getId() + " no fue encontrado en la base de datos");
        }
        return respuesta;
    }

    private Odontologo actualizarOdontologo(Odontologo odontologo, Odontologo odontologoNuevo) {
        if (odontologoNuevo.getNombre() != null) {
            odontologo.setNombre(odontologoNuevo.getNombre());
        }
        if (odontologoNuevo.getApellido() != null) {
            odontologo.setApellido(odontologoNuevo.getApellido());
        }
        if (odontologoNuevo.getMatricula() != null) {
            odontologo.setMatricula(odontologoNuevo.getMatricula());
        }
        return odontologo;
    }



}