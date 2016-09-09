package ir.ast;

import ir.ASTVisitor;

public class Body extends AST {
	protected Block block;

	public Body(){
		block = null;
	}

	public Body(Block b){
		block = b;
	}

	@Override
	public String toString() {
		return (block != null)? block.toString() : "extern;";
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}