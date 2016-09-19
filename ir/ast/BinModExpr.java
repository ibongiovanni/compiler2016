package ir.ast;

import ir.ASTVisitor;

public class BinModExpr extends ArithBinExpr {
	
	public BinModExpr (Expression lo, Expression ro){
		super(lo,BinOpType.MOD,ro);
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}