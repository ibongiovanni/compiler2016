package ir.ast;

import ir.ASTVisitor;

public class MethodCallStmt extends Statement {
	protected MethodCallExpr call;

	public MethodCallStmt(MethodCallExpr c){
		call = c;
	}

	public MethodCallExpr getCall(){
		return call;
	}

	@Override
	public String toString() {
		return call+";";
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

}