package ir.ast;

import ir.ASTVisitor;

public class BinGTExpr extends BinOpExpr {
	
	public BinGTExpr (Expression lo, Expression ro){
		operator = BinOpType.GE;
		lOperand = lo;
		rOperand = ro;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}