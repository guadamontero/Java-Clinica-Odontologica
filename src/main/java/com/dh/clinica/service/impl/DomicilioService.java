package com.dh.clinica.service.impl;


import com.dh.clinica.entities.Domicilio;
import com.dh.clinica.repository.DomicilioRepository;
import com.dh.clinica.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DomicilioService implements IService<Domicilio> {

    @Autowired
    private DomicilioRepository repository;


    @Override
    public Domicilio guardar(Domicilio domicilio) {
        return null;
    }

    @Override
    public Optional<Domicilio> buscar(Integer id) {
        return Optional.empty();
    }
    @Override
    public List<Domicilio> buscarTodos() {
        return null;
    }
    @Override
    public boolean eliminar(Integer id) {
        return true;
    }

    @Override
    public Domicilio actualizar(Domicilio domicilio) {
        if (domicilio.getCalle() != null) {
            domicilio.setCalle(domicilio.getCalle());
        }
        if (domicilio.getNumero() != null) {
            domicilio.setNumero(domicilio.getNumero());
        }
        if (domicilio.getLocalidad() != null) {
            domicilio.setLocalidad(domicilio.getLocalidad());
        }
        if (domicilio.getProvincia() != null) {
            domicilio.setProvincia(domicilio.getProvincia());
        }
        return domicilio;
    }


}