package ir.ast;

import ir.ASTVisitor;

public class BracketExpr extends Expression {
	
	public BracketExpr(Expression e){
		expr=e;
	}

	@Override
	public String toString() {
		return "("+expr+")";
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}