package com.dh.clinica.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DomicilioDTO {
    private Long id;
    private String calle;
    private String numero;
    private String localidad;
    private String provincia;
}