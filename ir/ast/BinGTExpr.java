package ir.ast;

import ir.ASTVisitor;

public class BinGTExpr extends RelBinExpr {
	
	public BinGTExpr (Expression lo, Expression ro){
		super(lo,BinOpType.GE,ro);
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}