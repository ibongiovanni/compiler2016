package ir.ast;

import ir.ASTVisitor;

public class BinAndExpr extends CondBinExpr {
	
	public BinAndExpr (Expression lo, Expression ro){
		super(lo,BinOpType.AND,ro);
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}