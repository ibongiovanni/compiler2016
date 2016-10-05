package ir.ast;

import ir.ASTVisitor;

public class NegatedExpr extends Expression {
	
	public NegatedExpr(Expression e){
		expr=e;
	}

	@Override
	public String toString() {
		return "!"+expr;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}