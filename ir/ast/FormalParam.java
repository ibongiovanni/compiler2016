package ir.ast;

import ir.ASTVisitor;

public class FormalParam extends NamedDecl {

	public FormalParam(Type t, String id){
		setType(t);
		this.id = id;
	}

	@Override
	public String toString() {
		return type.toString()+" "+id;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}