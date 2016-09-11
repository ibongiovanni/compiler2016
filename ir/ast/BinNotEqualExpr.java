package ir.ast;

import ir.ASTVisitor;

public class BinNotEqualExpr extends BinOpExpr {
	
	public BinNotEqualExpr (Expression lo, Expression ro){
		operator = BinOpType.NEQ;
		lOperand = lo;
		rOperand = ro;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}