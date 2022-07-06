package com.dh.clinica.service.impl;


import com.dh.clinica.entities.Odontologo;
import com.dh.clinica.repository.IOdontologoRepository;
import com.dh.clinica.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService implements IService<Odontologo> {

    @Autowired
    private IOdontologoRepository repository;


    public Odontologo guardar(Odontologo odontologo){
        return repository.save(odontologo);
    }

    @Override
    public Optional<Odontologo> buscar(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Odontologo> buscarTodos() {
        return new ArrayList<>(repository.findAll());
    }

    @Override
    public boolean eliminar(Long id) {
        Boolean respuesta;
        if(repository.findById(id).isPresent()){
            repository.deleteById(id);
            respuesta = true;
        } else {
            respuesta = false;
        }
        return respuesta;
    }

    @Override
    public Odontologo actualizar(Odontologo odontologo) {
        if (odontologo.getNombre() != null) {
            odontologo.setNombre(odontologo.getNombre());
        }
        if (odontologo.getApellido() != null) {
            odontologo.setApellido(odontologo.getApellido());
        }
        if (odontologo.getMatricula() != null) {
            odontologo.setMatricula(odontologo.getMatricula());
        }
        return odontologo;
    }

}