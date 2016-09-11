package ir.ast;

import ir.ASTVisitor;

public class BinLTExpr extends BinOpExpr {
	
	public BinLTExpr (Expression lo, Expression ro){
		operator = BinOpType.LE;
		lOperand = lo;
		rOperand = ro;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}