package ir.ast;

import ir.ASTVisitor;

public class BinDivideExpr extends BinOpExpr {
	
	public BinDivideExpr (Expression lo, Expression ro){
		operator = BinOpType.DIVIDE;
		lOperand = lo;
		rOperand = ro;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}