package ir.ast;

import ir.ASTVisitor;

public class ArrayLocation extends Location {
	protected Expression index;
	protected int blockId;

	public ArrayLocation(){
		
	}

	public ArrayLocation(String id, Expression ind) {
		this.id = id;
		index = ind;
		this.blockId = -1;
	}

	public int getBlockId() {
		return blockId;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}
	
	
	@Override
	public String toString() {
		return id+"["+index+"]";
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}