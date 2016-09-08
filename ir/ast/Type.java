package ir.ast;

public enum Type {
	INT,
	FLOAT,
	BOOL,
	INTARRAY,
	VOID,
	UNDEFINED;
	
	@Override
	public String toString() {
		switch(this) {
			case INT:
				return "int";
			case BOOL:
				return "bool";
			case FLOAT:
				return "float";
			case VOID:
				return "void";
			case UNDEFINED:
				return "undefined";
			case INTARRAY:
				return "int[]";
		}
		
		return null;
	}
	
	public boolean isArray() {
		if (this == Type.INTARRAY) {
			return true;
		}
		
		return false;
	}
}
