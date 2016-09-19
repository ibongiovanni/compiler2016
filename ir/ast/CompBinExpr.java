package ir.ast;

import ir.ASTVisitor;

public class CompBinExpr extends BinOpExpr {

	public CompBinExpr(Expression l, BinOpType op, Expression r){
		super(l,op,r);
	}
	
}