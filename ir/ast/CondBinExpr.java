package ir.ast;

import ir.ASTVisitor;

public class CondBinExpr extends BinOpExpr {
	
	public CondBinExpr(Expression l, BinOpType op, Expression r){
		super(l,op,r);
	}
	
}