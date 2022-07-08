package com.dh.clinica.service.impl;

import com.dh.clinica.entities.Odontologo;
import com.dh.clinica.entities.Paciente;
import com.dh.clinica.entities.Turno;
import com.dh.clinica.exceptions.BadRequestException;
import com.dh.clinica.exceptions.ResourceNotFoundException;
import com.dh.clinica.repository.ITurnoRepository;
import com.dh.clinica.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class TurnoService implements IService<Turno> {

    @Autowired
    private ITurnoRepository turnoRepository;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private OdontologoService odontologoService;

    private Boolean verificarDisponibilidad(Long idOdontologo, LocalDate fechaTurno) {
        Boolean respuesta = true;
        List<Turno> listaTurnos = turnoRepository.findAll();
        for (Turno turno : listaTurnos){
            if (turno.getOdontologo().getId().equals(idOdontologo) && turno.getFechaTurno().equals(fechaTurno)){
                respuesta = false;
            }
        }
        return respuesta;
    }

    @Override
    public Turno guardar(Turno turno) throws BadRequestException, ResourceNotFoundException {
        Turno respuesta;
        if (turno.getPaciente().getId() != null && turno.getOdontologo() != null){
            Paciente pacienteEncontrado = pacienteService.buscar(turno.getPaciente().getId());
            Odontologo odontologoEncontrado = odontologoService.buscar(turno.getOdontologo().getId());
            if (verificarDisponibilidad(odontologoEncontrado.getId(), turno.getFechaTurno())){
                respuesta = turnoRepository.save(turno);
                respuesta.setPaciente(pacienteEncontrado);
                respuesta.setOdontologo(odontologoEncontrado);
            } else {
                throw new ResourceNotFoundException("El odontologo con id " + odontologoEncontrado.getId() + " ya tiene un turno agendado en la fecha "+ turno.getFechaTurno());
            }
        } else {
            throw new BadRequestException("Faltan introducir el id del paciente y del odontologo para crear el turno");
        }
        return respuesta;
    }

    @Override
    public Turno buscar(Long id) throws ResourceNotFoundException {
        Turno turno = turnoRepository.findById(id).orElse(null);
        if (turno != null){
            return turno;
        } else {
            throw new ResourceNotFoundException ("No fue encontrado el odontologo con id " + id);
        }
    }

    @Override
    public Set<Turno> buscarTodos() throws ResourceNotFoundException{
        List<Turno> listaTurnos = turnoRepository.findAll();
        Set<Turno> turnos = new HashSet<>();
        for (Turno turno : listaTurnos){
            turnos.add(turno);
        }
        if (turnos.size() == 0){
            throw new ResourceNotFoundException("No hay pacientes aún cargados en la base de datos");
        }
        return turnos;
    }

    @Override
    public String eliminar(Long id) throws ResourceNotFoundException {
        String respuesta;
        if (turnoRepository.findById(id).isPresent()){
            turnoRepository.deleteById(id);
            respuesta = "Eliminado exitosamente";
        } else {
            throw new ResourceNotFoundException("No fue encontrado el turno en la base de datos");
        }
        return respuesta;
    }

    @Override
    public String actualizar(Turno turno) throws ResourceNotFoundException, BadRequestException {
        String respuesta;
        if (turno.getId()!=null){
            Optional<Turno> turnoActualizar = turnoRepository.findById(turno.getId());
            if (turnoActualizar.isPresent()){
                turnoRepository.save(this.actualizarTurno(turnoActualizar.get(), turno));
                respuesta = "Actualización con éxito del turno número: " + turno.getId();
            }
            else {
                throw new ResourceNotFoundException("No fue posible encontrar el turno en la base de datos");
            }
        } else {
            throw new BadRequestException("No se introdujo el id del turno a modificar");
        }
        return respuesta;
    }

    private Turno actualizarTurno(Turno turno, Turno turnoNuevo){
        if (turnoNuevo.getFechaTurno() != null){
            turno.setFechaTurno(turnoNuevo.getFechaTurno());
        }
        return turno;
    }
}