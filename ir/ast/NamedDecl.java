package ir.ast;

public abstract class NamedDecl extends Declaration {
	protected String id;

	protected int offset;

	public void setOffset(int o){
		offset=o;
	}

	public int getOffset(){
		return offset;
	}

	public String getId(){
		return id;
	}

	public void setId(String newId){
		id = newId;
	}

}