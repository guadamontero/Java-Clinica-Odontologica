package com.dh.clinica.controller;

import com.dh.clinica.entities.Turno;
import com.dh.clinica.service.impl.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("turnos")
public class TurnoController {

    @Autowired
    TurnoService turnoService;

    @GetMapping
    public ResponseEntity<ArrayList<Turno>> mostrarTurnos (){
        ArrayList<Turno> listarTurnos = turnoService.mostrarTodos();
        return ResponseEntity.ok(listarTurnos);
    }

    @GetMapping("/{id}")
    public Optional<Turno> mostrarTurnoPorId(@PathVariable Long id){
        return turnoService.mostrarPorId(id);
    }

    @PostMapping
    public Turno guardarTurno(@RequestBody Turno turno){
        return turnoService.guardar(turno);
    }
}