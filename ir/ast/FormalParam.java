package ir.ast;

import ir.ASTVisitor;

public class FormalParam extends NamedDecl {

	protected int number;
	public FormalParam(String t, String id){
		setType(t);
		this.id = id;
	}

	public void setNumber(int n){
		number=n;
	}
	
	public int getNumber(){
		return number;
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