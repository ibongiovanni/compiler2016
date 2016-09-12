package ir.ast;

import ir.ASTVisitor;

public class BinEqualExpr extends BinOpExpr {
	
	public BinEqualExpr (Expression lo, Expression ro){
		super(lo,BinOpType.CEQ,ro);
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}