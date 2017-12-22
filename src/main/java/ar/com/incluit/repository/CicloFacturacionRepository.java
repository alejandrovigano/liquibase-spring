package ar.com.incluit.repository;

import ar.com.incluit.domain.CicloFacturacion;
import ar.com.incluit.domain.ResolutorTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CicloFacturacionRepository extends JpaRepository<CicloFacturacion, Integer> {

}
