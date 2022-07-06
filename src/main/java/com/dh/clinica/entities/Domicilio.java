
package com.dh.clinica.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name= "domicilios")
@Getter @Setter
public class Domicilio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String calle;
    private String numero;
    private String localidad;
    private String provincia;

}
