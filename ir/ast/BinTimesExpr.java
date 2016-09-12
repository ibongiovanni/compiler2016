package ir.ast;

import ir.ASTVisitor;

public class BinTimesExpr extends BinOpExpr {
	
	public BinTimesExpr (Expression lo, Expression ro){
		super(lo,BinOpType.MULTIPLY,ro);
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}