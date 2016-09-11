package ir.ast;

import ir.ASTVisitor;

public class BinGOETExpr extends BinOpExpr {
	
	public BinGOETExpr (Expression lo, Expression ro){
		operator = BinOpType.GEQ;
		lOperand = lo;
		rOperand = ro;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}