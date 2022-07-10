package com.dh.clinica.service.impl;

import com.dh.clinica.entities.Odontologo;
import com.dh.clinica.entities.Paciente;
import com.dh.clinica.entities.Turno;
import com.dh.clinica.exceptions.BadRequestException;
import com.dh.clinica.exceptions.ResourceNotFoundException;
import com.dh.clinica.repository.ITurnoRepository;
import com.dh.clinica.service.IService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class TurnoService implements IService<Turno> {

    private final Logger logger = Logger.getLogger(TurnoService.class);

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
                logger.info("Se ha registrado el turno con exito");
            } else {
                throw new ResourceNotFoundException("El odontologo con id " + odontologoEncontrado.getId() + " ya tiene un turno agendado en la siguiente fecha: " + turno.getFechaTurno());
            }
        } else {
            throw new BadRequestException("Faltan introducir el id del paciente y del odontologo para registrar un turno");
        }
        return respuesta;
    }

    @Override
    public Turno buscar(Long id) throws ResourceNotFoundException {
        Turno turno = turnoRepository.findById(id).orElse(null);
        if (turno != null){
            logger.info("Se consultó el turno del paciente " + turno.getPaciente().getApellido() + ", y odontologo " + turno.getOdontologo().getApellido());
            return turno;
        } else {
            throw new ResourceNotFoundException ("No fue encontrado el turno con el siguiente id: " + id);
        }
    }

    @Override
    public Set<Turno> buscarTodos() throws ResourceNotFoundException{
        List<Turno> listaTurnos = turnoRepository.findAll();
        Set<Turno> turnos = new HashSet<>();
        for (Turno turno : listaTurnos){
            turnos.add(turno);
        }
        logger.info("Se consultaron todos los turnos registrados en la base de datos");
        if (turnos.size() == 0){
            throw new ResourceNotFoundException("No hay pacientes aún");
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
            throw new ResourceNotFoundException("El turno ingresado no existe");
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
                respuesta = "Actualización exitosa del turno número: " + turno.getId();
            }
            else {
                throw new ResourceNotFoundException("No fue posible encontrar el turno");
            }
        } else {
            throw new BadRequestException("Falta introducir el id del turno a modificar");
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