package ir.ast;

import ir.ASTVisitor;

public class IfStmt extends Statement {
	private Expression condition;
	private Statement ifStmt;
	private Statement elseStmt;
	
	public IfStmt(Expression cond, Statement ifSt) {
		this.condition = cond;
		this.ifStmt = ifSt;
		this.elseStmt = null;
	}
	
	public IfStmt(Expression cond, Statement ifSt, Statement elseSt) {
		this.condition = cond;
		this.ifStmt = ifSt;
		this.elseStmt = elseSt;
	}

	public Expression getCondition() {
		return condition;
	}

	public void setCondition(Expression condition) {
		this.condition = condition;
	}

	public Statement getIfStmt() {
		return ifStmt;
	}

	public void setIfStmt(Statement ifStmt) {
		this.ifStmt = ifStmt;
	}

	public Statement getElseStmt() {
		return elseStmt;
	}

	public void setElseStmt(Statement elseStmt) {
		this.elseStmt = elseStmt;
	}
	
	@Override
	public String toString() {
		String rtn = "if (" + condition + ")\n\t" + ifStmt.toString()+"\n";
		
		if (elseStmt != null) {
			rtn += "else \n\t" + elseStmt+"\n";
		}
		
		return rtn;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
