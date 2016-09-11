package ir.ast;

import ir.ASTVisitor;

public class BinMinusExpr extends BinOpExpr {
	
	public BinMinusExpr (Expression lo, Expression ro){
		operator = BinOpType.MINUS;
		lOperand = lo;
		rOperand = ro;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}