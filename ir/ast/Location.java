package ir.ast;

public abstract class Location extends Expression {
	protected String id;
	protected Declaration ref;

	public Declaration getRef(){
		return ref;
	}

	public void setRef(Declaration r){
		ref=r;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}


}
