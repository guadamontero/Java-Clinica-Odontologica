package com.dh.clinica.repository;

import com.dh.clinica.entities.Domicilio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDomicilioRepository extends JpaRepository<Domicilio, Long> {

}
