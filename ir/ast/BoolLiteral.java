package ir.ast;

import ir.ASTVisitor;

public class BoolLiteral extends Literal {
	private Boolean value;

	public BoolLiteral(Boolean value) {
		this.value = value;
	}

	public void setValue(Boolean newValue) {
		value = newValue;
	}

	public Boolean getValue() {
		return value;
	}

	@Override
	public Type getType() {
		return Type.INT;
	}
}