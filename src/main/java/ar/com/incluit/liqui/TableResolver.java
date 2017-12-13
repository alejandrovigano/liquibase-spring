package ar.com.incluit.liqui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import ar.com.incluit.domain.AbstractGrupo;

@Component
public class TableResolver {

	private static Logger LOGGER = LoggerFactory.getLogger(TableResolver.class);

	@Autowired
	private Environment env;

	public String obtenerTabla(AbstractGrupo grupoTipo) {
		String tabla = env.getProperty("tabla-grupo-" + grupoTipo.getPrefix() + "-" + grupoTipo.getId());
		LOGGER.info("tabla-grupo-" + grupoTipo.getPrefix() + "-" + grupoTipo.getId());
		return tabla;
	}

}
