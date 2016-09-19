package ir.ast;

import ir.ASTVisitor;

public class BinPlusExpr extends ArithBinExpr {
	
	public BinPlusExpr (Expression lo, Expression ro){
		super(lo,BinOpType.PLUS,ro);
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}