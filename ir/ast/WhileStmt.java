package ir.ast;

import ir.ASTVisitor;

public class WhileStmt extends Statement {
	private Expression condition;
	private Statement body;

	public WhileStmt(Expression c, Statement b){
		condition = c;
		body = b;
	}

	public Expression getCondition(){
		return condition;
	}

	public Statement getBody(){
		return body;
	}

	@Override
	public String toString() {
		return "while "+condition+"\n\t"+body;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

}