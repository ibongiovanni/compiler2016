package ir.ast;

import ir.ASTVisitor;

public class ArrayLocation extends Location {
	private Integer index;
	private int blockId;

	public ArrayLocation(String id, Integer ind) {
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