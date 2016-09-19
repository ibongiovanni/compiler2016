package ir.ast;

import ir.ASTVisitor;

public class ArithBinExpr extends BinOpExpr {
	
	public ArithBinExpr(Expression l, BinOpType op, Expression r){
		super(l,op,r);
	}
}