package com.dh.clinica.controller;

import com.dh.clinica.entities.Odontologo;
import com.dh.clinica.exceptions.BadRequestException;
import com.dh.clinica.exceptions.ResourceNotFoundException;
import com.dh.clinica.service.impl.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RestController
@RequestMapping("odontologos")
public class OdontologoController {

    @Autowired
    OdontologoService odontologoService;

    @PostMapping("/registrar")
    public ResponseEntity<Odontologo> guardar(@RequestBody Odontologo odontologo) {
        ResponseEntity<Odontologo> respuesta = ResponseEntity.badRequest().body(odontologo);
        Odontologo odontologoRegistrado = odontologoService.guardar(odontologo);
        if (odontologoRegistrado != null){
            respuesta = ResponseEntity.ok(odontologoRegistrado);
        }
        return respuesta;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(Long id) throws ResourceNotFoundException, BadRequestException {
        return ResponseEntity.ok(odontologoService.buscar(id));
    }

    @GetMapping("/todos")
    public ResponseEntity<Set<Odontologo>> buscarTodos() throws ResourceNotFoundException {
        return ResponseEntity.ok(odontologoService.buscarTodos());
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(odontologoService.eliminar(id));
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizar(@RequestBody Odontologo odontologo) throws BadRequestException, ResourceNotFoundException {
        ResponseEntity<String> respuesta;
        if (odontologo.getId() != null){
            respuesta = ResponseEntity.ok(odontologoService.actualizar(odontologo));
        } else{
            throw new BadRequestException("Falta el id del odontologo");
        }
        return respuesta;
    }
}