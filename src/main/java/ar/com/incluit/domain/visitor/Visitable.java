package ar.com.incluit.domain.visitor;

public interface Visitable {

	void accept(Visitor visitor);
	
}
