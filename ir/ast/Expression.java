package ir.ast;

public abstract class Expression extends AST {
	protected Expression expr;
	protected String type;
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String t) {
		this.type = t;
	}

	public Expression getExpression(){
		return expr;
	}
}
