package ir.ast;

import ir.ASTVisitor;

public class ArrayDecl extends VarDecl {
	protected int size; 

	public ArrayDecl(String id, Integer size){
		super(id);
		this.size = size.intValue();
	}

	@Override
	public String toString() {
		return id+"["+size+"]";
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

}