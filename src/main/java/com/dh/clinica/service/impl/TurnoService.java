package com.dh.clinica.service.impl;

import com.dh.clinica.entities.Turno;
import com.dh.clinica.repository.ITurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class TurnoService {

    @Autowired
    private ITurnoRepository repository;

    public ArrayList<Turno> mostrarTodos() {
        return new ArrayList<>(repository.findAll());
    }

    public Optional<Turno> mostrarPorId(Long id){
        return repository.findById(id);
    }

    public Turno guardar(Turno turno){
        return repository.save(turno);
    }

    public void eliminarPorId(Long id){
        repository.deleteById(id);
    }
}