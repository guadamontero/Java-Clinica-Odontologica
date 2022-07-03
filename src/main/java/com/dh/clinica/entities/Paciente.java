package com.dh.clinica.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name ="pacientes")
@Getter @Setter
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private String sexo;
    private String telefono;
    private Date fecha_ingreso = new Date();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="domicilio_id", referencedColumnName = "id",nullable = false)
    private Domicilio domicilio;

    @OneToMany(mappedBy = "paciente")
    @JsonIgnore
    private Set<Turno> turnos;


}