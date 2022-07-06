package com.dh.clinica.controller;

import com.dh.clinica.entities.Odontologo;
import com.dh.clinica.service.impl.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("odontologos")
public class OdontologoController {

    @Autowired
    OdontologoService odontologoService;


    @GetMapping
    public ResponseEntity<List<Odontologo>> mostrarOdontologo (){
        List<Odontologo> listarOdontologos = odontologoService.buscarTodos();
        return ResponseEntity.ok(listarOdontologos);
    }

    @GetMapping("/{id}")
    public Optional<Odontologo> mostrarOdontologoPorId(@PathVariable Long id){
        return odontologoService.buscar(id);
    }

    @PostMapping
    public Odontologo guardarOdontologo(@RequestBody Odontologo odontologo){
        return odontologoService.guardar(odontologo);
    }

    @DeleteMapping("/{id}")
    public void eliminarOdontologoPorId(@PathVariable Long id){
        odontologoService.eliminar(id);
    }
}