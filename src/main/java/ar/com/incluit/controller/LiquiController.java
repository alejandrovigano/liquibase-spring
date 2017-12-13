package ar.com.incluit.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.incluit.domain.AbstractGrupo;
import ar.com.incluit.domain.AbstractParameter;
import ar.com.incluit.domain.Tipo;
import ar.com.incluit.liqui.ChangeLogJsonBuilder;
import ar.com.incluit.liqui.InsertBuilder;
import ar.com.incluit.liqui.LiquiChangeLogBuilder;
import ar.com.incluit.liqui.TableResolver;
import ar.com.incluit.liqui.changelog.Insert;
import ar.com.incluit.liqui.changelog.LiquiChangelog;
import ar.com.incluit.repository.EstadoRepository;
import ar.com.incluit.repository.GrupoEstadoRepository;
import ar.com.incluit.repository.GrupoTipoRepository;
import ar.com.incluit.repository.TipoRepository;

@RestController
@RequestMapping("/build")
public class LiquiController {

	@Autowired
	private TipoRepository tipoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private GrupoTipoRepository grupoTipoRepository;

	@Autowired
	private GrupoEstadoRepository grupoEstadoRepository;

	@Autowired
	private InsertBuilder insertBuilder;

	@Autowired
	private ChangeLogJsonBuilder jsonBuilder;

	@Autowired
	private TableResolver tableResolver;

	@Autowired
	private LiquiChangeLogBuilder liquiChangeLogBuilder;

	@Value("${path}")
	private String path;

	@Value("${subfolder}")
	private String subfolder;

	@GetMapping
	public String buildJson() throws Exception {
		List<Tipo> tipos = tipoRepository.findAll();
		List<Insert> inserts = insertBuilder.buildInserts(tipos);
		LiquiChangelog liqui = liquiChangeLogBuilder.buildLiquiInsertChangelog(inserts);
		return jsonBuilder.buildChangeLogJson(liqui);
	}

	@GetMapping("/tipo")
	public String buildJsonGroupedTipo() throws Exception {
		Iterable<? extends AbstractGrupo> grupos = grupoTipoRepository.findAll();
		buildJsonAndWriteFile(grupos, tipoRepository::findByGrupoTipoIdGrupoTipo,"tipo");
		return "ok";
	}

	@GetMapping("/estado")
	public String buildJsonGroupedEstado() throws Exception {
		Iterable<? extends AbstractGrupo> grupos = grupoEstadoRepository.findAll();
		buildJsonAndWriteFile(grupos, estadoRepository::findByGrupoEstadoIdGrupoEstado,"estado");
		return "ok";
	}

	private void buildJsonAndWriteFile(Iterable<? extends AbstractGrupo> grupos,
			Function<Integer, List<? extends AbstractParameter>> findByGrupo, String prefix) throws Exception, IOException {
		List<String> files = new ArrayList<>();

		for (AbstractGrupo grupoTipo : grupos) {
			List<? extends AbstractParameter> tipos = findByGrupo.apply(grupoTipo.getId());

			List<Insert> inserts = insertBuilder.buildInserts(tipos);
			LiquiChangelog liqui = liquiChangeLogBuilder.buildLiquiInsertChangelog(inserts);

			String filename = obtenerFilename(grupoTipo);
			String filePath = prefix + "/" + subfolder + "/" + filename;
			String relativeFilepath = subfolder + "/" + filename;
			files.add(relativeFilepath);

			writeToFile(liqui, filePath);
		}

		LiquiChangelog liquiIndex = liquiChangeLogBuilder.buildFileIncludeChangelog(files);
		writeToFile(liquiIndex, prefix + "/index.json");
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
		String date = new SimpleDateFormat("yyyyMMddS").format(new Date());
		date = StringUtils.rightPad(date, 12, "0");
		return date + "-seed-" + tableResolver.obtenerTabla(grupoTipo) + ".json";
	}
}
