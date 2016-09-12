package ir.ast;

import ir.ASTVisitor;

public class BinLOETExpr extends BinOpExpr {
	
	public BinLOETExpr (Expression lo, Expression ro){
		super(lo,BinOpType.LEQ,ro);
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}