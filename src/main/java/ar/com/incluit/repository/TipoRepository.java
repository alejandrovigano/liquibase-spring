package ar.com.incluit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.incluit.domain.Tipo;

public interface TipoRepository extends JpaRepository<Tipo, Integer> {

	List<Tipo> findByGrupoTipoIdGrupoTipo(Integer grupoTipo);

}
