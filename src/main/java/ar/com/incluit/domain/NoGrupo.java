package ar.com.incluit.domain;

public class NoGrupo extends AbstractGrupo {

	private String prefix;

	public NoGrupo(String prefix) {
		super();
		this.prefix = prefix;
	}

	@Override
	public Integer getId() {
		return 0;
	}

	@Override
	public String getPrefix() {
		return prefix;
	}

}
