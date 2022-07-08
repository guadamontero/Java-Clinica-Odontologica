package com.dh.clinica.controller;

import com.dh.clinica.entities.Turno;
import com.dh.clinica.exceptions.BadRequestException;
import com.dh.clinica.exceptions.ResourceNotFoundException;
import com.dh.clinica.service.impl.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("turnos")
public class TurnoController {

    @Autowired
    TurnoService turnoService;

    @PostMapping("/registrar")
    public ResponseEntity<?> guardar(@RequestBody Turno turnoDTO) throws BadRequestException, ResourceNotFoundException {
        return ResponseEntity.ok(turnoService.guardar(turnoDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(Long id) throws ResourceNotFoundException, BadRequestException {
        return ResponseEntity.ok(turnoService.buscar(id));
    }

    @GetMapping("/todos")
    public ResponseEntity<?> buscarTodos() throws ResourceNotFoundException {
        return ResponseEntity.ok(turnoService.buscarTodos());
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(turnoService.eliminar(id));
    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizar(@RequestBody Turno turno) throws ResourceNotFoundException, BadRequestException{
        return ResponseEntity.ok(turnoService.actualizar(turno));
    }

}