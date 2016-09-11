package ir.ast;

import ir.ASTVisitor;

public class BinLOETExpr extends BinOpExpr {
	
	public BinLOETExpr (Expression lo, Expression ro){
		operator = BinOpType.LEQ;
		lOperand = lo;
		rOperand = ro;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}