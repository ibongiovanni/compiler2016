package ir.ast;

import ir.ASTVisitor;

public class BoolLiteral extends Literal {
	private Boolean value;

	public BoolLiteral(Boolean value) {
		this.value = value;
		setType(Type.BOOL);
	}

	public void setValue(Boolean newValue) {
		value = newValue;
	}

	public Boolean getValue() {
		return value;
	}

	@Override
	public String toString() {
		return (value)? "true":"false";
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

}