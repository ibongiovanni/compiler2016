package ir.ast;

import ir.ASTVisitor;

public class BinPlusExpr extends BinOpExpr {
	
	public BinPlusExpr (Expression lo, Expression ro){
		operator = BinOpType.PLUS;
		lOperand = lo;
		rOperand = ro;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}