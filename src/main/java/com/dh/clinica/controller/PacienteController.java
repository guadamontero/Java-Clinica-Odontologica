package com.dh.clinica.controller;



import com.dh.clinica.entities.Paciente;
import com.dh.clinica.service.impl.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    PacienteService pacienteService;


    @GetMapping("/todos")
    public ResponseEntity<List<Paciente>> mostrarPacientes (){
        List<Paciente> listarPaciente = pacienteService.buscarTodos();
        return ResponseEntity.ok(listarPaciente);
    }

    @GetMapping("/{id}")
    public Optional<Paciente> mostrarPacientePorId(@PathVariable Integer id){
        return pacienteService.buscar(id);
    }

    @PostMapping
    public Paciente guardarPaciente(@RequestBody Paciente paciente){
        return pacienteService.guardar(paciente);
    }

    @DeleteMapping("/{id}")
    public void eliminarPacientePorId(@PathVariable Integer id){
        pacienteService.eliminar(id);
    }
}