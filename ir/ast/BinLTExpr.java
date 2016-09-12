package ir.ast;

import ir.ASTVisitor;

public class BinLTExpr extends BinOpExpr {
	
	public BinLTExpr (Expression lo, Expression ro){
		super(lo,BinOpType.LE,ro);
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}