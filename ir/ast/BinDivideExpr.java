package ir.ast;

import ir.ASTVisitor;

public class BinDivideExpr extends ArithBinExpr {
	
	public BinDivideExpr (Expression lo, Expression ro){
		super(lo,BinOpType.DIVIDE,ro);
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}