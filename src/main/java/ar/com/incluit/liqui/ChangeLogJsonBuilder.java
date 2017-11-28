package ar.com.incluit.liqui;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.com.incluit.liqui.changelog.LiquiChangelog;

@Component
public class ChangeLogJsonBuilder {

	public String buildChangeLogJson(LiquiChangelog liqui) throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);

		return StringUtils.stripAccents(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(liqui));
	}

}
