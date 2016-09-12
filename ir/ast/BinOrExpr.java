package ir.ast;

import ir.ASTVisitor;

public class BinOrExpr extends BinOpExpr {
	
	public BinOrExpr (Expression lo, Expression ro){
		super(lo,BinOpType.OR,ro);
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}