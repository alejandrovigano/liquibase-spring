package ar.com.incluit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.incluit.domain.Mensaje;

public interface MensajeRepository extends JpaRepository<Mensaje, Integer> {

}
