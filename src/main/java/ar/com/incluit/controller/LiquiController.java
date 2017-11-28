package ar.com.incluit.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.incluit.domain.GrupoTipo;
import ar.com.incluit.domain.Tipo;
import ar.com.incluit.liqui.ChangeLogJsonBuilder;
import ar.com.incluit.liqui.InsertBuilder;
import ar.com.incluit.liqui.LiquiChangeLogBuilder;
import ar.com.incluit.liqui.TableResolver;
import ar.com.incluit.liqui.changelog.Insert;
import ar.com.incluit.liqui.changelog.LiquiChangelog;
import ar.com.incluit.repository.GrupoTipoRepository;
import ar.com.incluit.repository.TipoRepository;

@RestController
@RequestMapping("/build")
public class LiquiController {

	@Autowired
	private TipoRepository repository;

	@Autowired
	private GrupoTipoRepository grupoTipoRepository;

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
		List<Tipo> tipos = repository.findAll();
		List<Insert> inserts = insertBuilder.buildInserts(tipos);
		LiquiChangelog liqui = liquiChangeLogBuilder.buildLiquiInsertChangelog(inserts);
		return jsonBuilder.buildChangeLogJson(liqui);
	}

	@GetMapping("/groupsave")
	public String buildJsonGrouped() throws Exception {

		Iterable<GrupoTipo> grupos = grupoTipoRepository.findAll();

		List<String> files = new ArrayList<>();

		for (GrupoTipo grupoTipo : grupos) {
			List<Tipo> tipos = repository.findByGrupoTipo(grupoTipo);

			List<Insert> inserts = insertBuilder.buildInserts(tipos);
			LiquiChangelog liqui = liquiChangeLogBuilder.buildLiquiInsertChangelog(inserts);

			String filename = subfolder + "/" + obtenerFilename(grupoTipo);
			files.add(filename);

			writeToFile(liqui, filename);
		}

		LiquiChangelog liquiIndex = liquiChangeLogBuilder.buildFileIncludeChangelog(files);
		writeToFile(liquiIndex, "index.json");

		return "ok";
	}

	private void writeToFile(LiquiChangelog liqui, String filename) throws Exception, IOException {
		String json = jsonBuilder.buildChangeLogJson(liqui);
		Path filePath = Paths.get(path + "/" + filename);

		if (!Files.exists(filePath.getParent())) {
			Files.createDirectories(filePath.getParent());
		}
		Files.write(filePath, json.getBytes());
	}

	private String obtenerFilename(GrupoTipo grupoTipo) {
		String date = new SimpleDateFormat("yyyyMMddS").format(new Date());
		date = StringUtils.rightPad(date, 12, "0");
		return date + "-" + tableResolver.obtenerTabla(grupoTipo) + "-insert.json";
	}
}
