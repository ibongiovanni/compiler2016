package ir.ast;

public abstract class Declaration extends AST {
	protected String type;
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String t) {
		this.type = t;
	}
}
