package ir.ast;

import ir.ASTVisitor;

public class BinOrExpr extends BinOpExpr {
	
	public BinOrExpr (Expression lo, Expression ro){
		operator = BinOpType.OR;
		lOperand = lo;
		rOperand = ro;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}