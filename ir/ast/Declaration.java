package ir.ast;

public abstract class Declaration extends AST {
	protected Type type;
	
	public Type getType() {
		return this.type;
	}
	
	public void setType(Type t) {
		this.type = t;
	}
}
