package ir.ast;

import ir.ASTVisitor;

public class RelBinExpr extends BinOpExpr {
	
	public RelBinExpr(Expression l, BinOpType op, Expression r){
		super(l,op,r);
	}
	
}