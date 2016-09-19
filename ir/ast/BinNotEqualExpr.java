package ir.ast;

import ir.ASTVisitor;

public class BinNotEqualExpr extends CompBinExpr {
	
	public BinNotEqualExpr (Expression lo, Expression ro){
		super(lo,BinOpType.NEQ,ro);
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}