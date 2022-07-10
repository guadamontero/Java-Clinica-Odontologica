package com.dh.clinica.service.impl;


import com.dh.clinica.entities.Domicilio;
import com.dh.clinica.exceptions.ResourceNotFoundException;
import com.dh.clinica.repository.IDomicilioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DomicilioService {

    @Autowired
    private IDomicilioRepository domicilioRepository;

    @Autowired
    private ObjectMapper mapper;

    public Domicilio actualizar(Domicilio domicilio) throws ResourceNotFoundException {
        Optional<Domicilio> domicilioActualizar = domicilioRepository.findById(domicilio.getId());
        if (domicilioActualizar.isPresent()) {
            Domicilio actualizado = this.actualizarDomicilio(domicilioActualizar.get(), domicilio);
            Domicilio guardado = domicilioRepository.save(actualizado);
            return mapper.convertValue(guardado, Domicilio.class);
        }
        else {
            throw new ResourceNotFoundException("El domicilio con id " + domicilio.getId() +" no fue encontrado");
        }
    }

    private Domicilio actualizarDomicilio(Domicilio domicilio, Domicilio domicilioNuevo) {
        if (domicilioNuevo.getCalle() != null) {
            domicilio.setCalle(domicilioNuevo.getCalle());
        }
        if (domicilioNuevo.getNumero() != null) {
            domicilio.setNumero(domicilioNuevo.getNumero());
        }
        if (domicilioNuevo.getLocalidad() != null) {
            domicilio.setLocalidad(domicilioNuevo.getLocalidad());
        }
        if (domicilioNuevo.getProvincia() != null) {
            domicilio.setProvincia(domicilioNuevo.getProvincia());
        }
        return domicilio;
    }

}