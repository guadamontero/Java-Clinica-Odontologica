package com.dh.clinica.controller;

import com.dh.clinica.entities.Odontologo;
import com.dh.clinica.entities.Paciente;
import com.dh.clinica.exceptions.BadRequestException;
import com.dh.clinica.exceptions.ResourceNotFoundException;
import com.dh.clinica.service.impl.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    PacienteService pacienteService;


    @PostMapping("/registrar")
    public ResponseEntity<Paciente> guardar(@RequestBody Paciente paciente) {
        ResponseEntity<Paciente> respuesta = null;
        paciente.setFechaIngreso(LocalDateTime.now());
        Paciente pacienteRegistrado = pacienteService.guardar(paciente);
        if (pacienteRegistrado != null) {
            respuesta = ResponseEntity.ok(pacienteRegistrado);
        }
        return respuesta;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(pacienteService.buscar(id));
    }

    @GetMapping("/todos")
    public ResponseEntity<Set<Paciente>> buscarTodos() throws ResourceNotFoundException {
        return ResponseEntity.ok(pacienteService.buscarTodos());
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(pacienteService.eliminar(id));
    }

    @PutMapping
    public ResponseEntity<String> actualizar(@RequestBody Paciente paciente) throws BadRequestException,ResourceNotFoundException {
        ResponseEntity<String> respuesta;
        if(paciente.getId() != null ){
            respuesta = ResponseEntity.ok(pacienteService.actualizar(paciente));
        } else {
            throw new BadRequestException("Id del paciente o del domicilio faltantes");
        }
        return respuesta;
    }
}