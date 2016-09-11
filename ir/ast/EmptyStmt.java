package ir.ast;

import ir.ASTVisitor;

public class EmptyStmt extends Statement {
	
	@Override
	public String toString() {
		return ";";
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}