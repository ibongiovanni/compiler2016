package ir.ast;

import ir.ASTVisitor;

public class BinModExpr extends BinOpExpr {
	
	public BinModExpr (Expression lo, Expression ro){
		operator = BinOpType.MOD;
		lOperand = lo;
		rOperand = ro;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}