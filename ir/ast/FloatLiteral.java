package ir.ast;

import ir.ASTVisitor;

public class FloatLiteral extends Literal {
	private Float value;

	public FloatLiteral(Float value) {
		this.value = value;
		setType(Type.FLOAT);
	}

	public FloatLiteral(String value) {
		this.value = new Float(value);
		setType(Type.FLOAT);
	}

	public void setValue(Float newValue) {
		value = newValue;
	}

	public Float getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value.toString();
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

}