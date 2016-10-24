package ir.ast;

import ir.ASTVisitor;

public class VarDecl extends NamedDecl {
	
	protected boolean locked;

	protected boolean isAtt;
	protected int attPos;

	public VarDecl(String id){
		this.id = id;
	}

	public VarDecl(String t, String id){
		this.id = id;
		setType(t);
	}

	public VarDecl(){
		
	}

	public void lock(){
		locked = true;
	}

	public boolean isLocked(){
		return locked;
	}

	public void newAtt(){
		isAtt=true;
	}

	public boolean isAtt(){
		return isAtt;
	}

	public void setAttPos(int pos){
		attPos=pos;
	}

	public int getAttPos(){
		return attPos;
	}

	@Override
	public String toString() {
		return id;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}