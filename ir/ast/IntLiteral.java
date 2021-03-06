package ir.ast;

import ir.ASTVisitor;

public class IntLiteral extends Literal {
	private String rawValue;
	private Integer value;
	
	/*
	 * Constructor for int literal that takes a string as an input
	 * @param: String integer
	 */
	public IntLiteral(String val){
		rawValue = val; // Will convert to int value in semantic check
		value = null;
		setType("INT");

	}

	public IntLiteral(Integer val){
		rawValue = val.toString();
		value = val;
		setType("INT");
	}


	public String getStringValue() {
		return rawValue;
	}

	public void setStringValue(String stringValue) {
		this.rawValue = stringValue;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public String getRawValue() {
		return rawValue;
	}

	public void setRawValue(String rawValue) {
		this.rawValue = rawValue;
	}

	@Override
	public String toString() {
		return rawValue;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
