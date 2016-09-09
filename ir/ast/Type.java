package ir.ast;

public enum Type {
	INT,
	FLOAT,
	BOOL,
	INTARRAY,
	FLOATARRAY,
	BOOLARRAY,
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
			case FLOATARRAY:
				return "float[]";
			case BOOLARRAY:
				return "bool[]";
			
		}
		
		return null;
	}
	
	public boolean isArray() {
		if (this == Type.INTARRAY || this == Type.FLOATARRAY || this == Type.BOOLARRAY) {
			return true;
		}
		
		return false;
	}
}
