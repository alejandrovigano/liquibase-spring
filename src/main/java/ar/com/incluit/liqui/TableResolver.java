package ar.com.incluit.liqui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import ar.com.incluit.domain.GrupoTipo;

@Component
public class TableResolver {

	private static Logger LOGGER = LoggerFactory.getLogger(TableResolver.class);

	@Autowired
	private Environment env;

	public String obtenerTabla(GrupoTipo grupoTipo) {
		String tabla = env.getProperty("tabla-grupo-" + grupoTipo.getIdGrupoTipo());
		LOGGER.info("GRUPO:" + grupoTipo + " TABLA: " + tabla);
		return tabla;
	}

}
