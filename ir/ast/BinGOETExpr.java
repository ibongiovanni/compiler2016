package ir.ast;

import ir.ASTVisitor;

public class BinGOETExpr extends RelBinExpr {
	
	public BinGOETExpr (Expression lo, Expression ro){
		super(lo,BinOpType.GEQ,ro);
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}