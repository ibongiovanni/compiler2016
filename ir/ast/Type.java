package ir.ast;

public class Type {
	
	public static final String UNDEFINED = "UNDEFINED";

	public static boolean isBasic(String t){
		switch (t) {
			case "INT": case "FLOAT": case "BOOL": case "INTARRAY": case "FLOATARRAY": case	"BOOLARRAY":
				return true;
			default:
				return false;
		}
	}
}
