package ar.com.incluit.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import ar.com.incluit.liqui.*;
import ar.com.incluit.liqui.changelog.Precondition;
import ar.com.incluit.liqui.changelog.RollBack;
import ar.com.incluit.repository.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.incluit.domain.AbstractGrupo;
import ar.com.incluit.domain.AbstractParameter;
import ar.com.incluit.domain.NoGrupo;
import ar.com.incluit.domain.Tipo;
import ar.com.incluit.liqui.changelog.Insert;
import ar.com.incluit.liqui.changelog.LiquiChangelog;

@RestController
@RequestMapping("/build")
public class LiquiController {

	private static Logger LOGGER = LoggerFactory.getLogger(LiquiController.class);

	@Autowired
	private TipoRepository tipoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private MensajeRepository mensajeRepository;

	@Autowired
	private ResolutorTransactionRepository resolutorTransactionRepository;

	@Autowired
	private CicloFacturacionRepository cicloFacturacionRepository;

	@Autowired
	private CanalAdhesionRepository canalAdhesionRepository;

	@Autowired
	private GrupoTipoRepository grupoTipoRepository;

	@Autowired
	private GrupoEstadoRepository grupoEstadoRepository;

	@Autowired
	private InsertBuilder insertBuilder;

	@Autowired
	private PreconditionBuilder preconditionBuilder;

	@Autowired
	private ChangeLogJsonBuilder jsonBuilder;

	@Autowired
	private RollbackBuilder rollbackBuilder;
	
	@Autowired
	private TableResolver tableResolver;

	@Autowired
	private LiquiChangeLogBuilder liquiChangeLogBuilder;

	@Value("${path}")
	private String path;

	@Value("${subfolder}")
	private String subfolder;

	@GetMapping("/all")
	public String buildAll() throws Exception {
		buildJsonGroupedCanal();
		buildJsonGroupedCiclo();
		buildJsonGroupedEstado();
		buildJsonGroupedMensaje();
		buildJsonGroupedResolutor();
		buildJsonGroupedTipo();

		return "ok";
	}

	@GetMapping("/tipo")
	public String buildJsonGroupedTipo() throws Exception {
		Iterable<? extends AbstractGrupo> grupos = grupoTipoRepository.findAll();
		buildJsonAndWriteFile(grupos, tipoRepository::findByGrupoTipoIdGrupoTipo, "tipo");
		return "ok";
	}

	@GetMapping("/estado")
	public String buildJsonGroupedEstado() throws Exception {
		Iterable<? extends AbstractGrupo> grupos = grupoEstadoRepository.findAll();
		buildJsonAndWriteFile(grupos, estadoRepository::findByGrupoEstadoIdGrupoEstado, "estado");
		return "ok";
	}

	@GetMapping("/mensaje")
	public String buildJsonGroupedMensaje() throws Exception {
		LOGGER.info("conteo " + mensajeRepository.count());
		buildJsonAndWriteFile(Collections.singletonList(new NoGrupo("mensaje")), x -> mensajeRepository.findAll(),
				"mensaje");
		return "ok";
	}

	@GetMapping("/resolutor")
	public String buildJsonGroupedResolutor() throws Exception {
		buildJsonAndWriteFile(Collections.singletonList(new NoGrupo("resolutor")),
				x -> resolutorTransactionRepository.findAll(), "resolutor");
		return "ok";
	}

	@GetMapping("/ciclo")
	public String buildJsonGroupedCiclo() throws Exception {
		buildJsonAndWriteFile(Collections.singletonList(new NoGrupo("ciclo")),
				x -> cicloFacturacionRepository.findAll(), "ciclo");
		return "ok";
	}

	@GetMapping("/canal")
	public String buildJsonGroupedCanal() throws Exception {
		buildJsonAndWriteFile(Collections.singletonList(new NoGrupo("canal")),
				x -> canalAdhesionRepository.findAll(), "canal");
		return "ok";
	}


	private void buildJsonAndWriteFile(Iterable<? extends AbstractGrupo> grupos,
			Function<Integer, List<? extends AbstractParameter>> findByGrupo, String prefix)
			throws Exception, IOException {
		List<String> files = new ArrayList<>();

		for (AbstractGrupo grupoTipo : grupos) {
			List<? extends AbstractParameter> params = findByGrupo.apply(grupoTipo.getId());

			List<Precondition> preconditiosn = preconditionBuilder.buildPreconditions(params);
			List<RollBack> rollbacks = rollbackBuilder.buildRollBack(params);
			List<Insert> inserts = insertBuilder.buildInserts(params);
			LiquiChangelog liqui = liquiChangeLogBuilder.buildLiquiInsertChangelog(inserts, preconditiosn, rollbacks);

			String filename = obtenerFilename(grupoTipo);
			String filePath = prefix + "/" + subfolder + "/" + filename;
			String relativeFilepath = subfolder + "/" + filename;
			files.add(relativeFilepath);

			writeToFile(liqui, filePath);
		}

		LiquiChangelog liquiIndex = liquiChangeLogBuilder.buildFileIncludeChangelog(files);
		writeToFile(liquiIndex, prefix + "/changelog-index.json");
	}

	private void writeToFile(LiquiChangelog liqui, String filename) throws Exception, IOException {
		String json = jsonBuilder.buildChangeLogJson(liqui);
		Path filePath = Paths.get(path + "/" + filename);

		if (!Files.exists(filePath.getParent())) {
			Files.createDirectories(filePath.getParent());
		}
		Files.write(filePath, json.getBytes());
	}

	private String obtenerFilename(AbstractGrupo grupoTipo) {
		String date = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
		date = StringUtils.rightPad(date, 12, "0");
		return date + "-Seed-" + toCamelCase(tableResolver.obtenerTabla(grupoTipo)) + ".json";
	}

	private String toCamelCase(String s) {
		return WordUtils.capitalizeFully(s.replace("_", " ")).replace(" ", "_");
	}
}
