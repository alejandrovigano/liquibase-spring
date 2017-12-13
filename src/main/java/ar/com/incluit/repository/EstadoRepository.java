package ar.com.incluit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.incluit.domain.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Integer> {

	List<Estado> findByGrupoEstadoIdGrupoEstado(Integer grupoTipo);

}
