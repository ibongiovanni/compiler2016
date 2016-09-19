package ir.ast;

public abstract class NamedDecl extends Declaration {
	protected String id;

	public String getId(){
		return id;
	}

}