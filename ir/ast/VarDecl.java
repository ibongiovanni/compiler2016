package ir.ast;

import ir.ASTVisitor;

public class VarDecl extends Declaration {
	protected String id;
	
	public VarDecl(String id){
		this.id = id;
	}

	@Override
	public String toString() {
		return id;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}