package ir.ast;

import ir.ASTVisitor;

public class ForStmt extends Statement {
	private String id;
	private Expression expr1;
	private Expression expr2;
	private Statement body;

	public ForStmt(String id, Expression e1, Expression e2, Statement b){
		this.id = id;
		expr1 = e1;
		expr2 = e2;
		body = b;
	}

	public String getId(){
		return id;
	}

	public Expression getExpr1(){
		return expr1;
	}

	public Expression getExpr2(){
		return expr2;
	}

	public Statement getBody(){
		return body;
	}

	@Override
	public String toString() {
		return "for "+id+"="+expr1+","+expr2+"\n\t"+body;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}