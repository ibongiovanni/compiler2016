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

	public static boolean isArray(String t){
		switch (t) {
			case "INTARRAY": case "FLOATARRAY": case "BOOLARRAY":
				return true;
			default:
				return false;
		}
	}

	public static boolean isNumerical(String t){
		switch (t) {
			case "INT": case "FLOAT": case "INTARRAY": case "FLOATARRAY":
				return true;
			default:
				return false;
		}
	}

	public static boolean isBoolean(String t){
		switch (t) {
			case "BOOL": case "BOOLARRAY":
				return true;
			default:
				return false;
		}
	}

	public static boolean areCompatible(String t1, String t2){
		if (t1.equals(t2)) {
			return true;
		}
		if (t1.endsWith("ARRAY")) {
			return t1.contains(t2);
		}
		if (t2.endsWith("ARRAY")) {
			return t2.contains(t1);
		}
		return false;
		
	}

	//Converts array tipe to single tipe
	public static String toSingle(String at){
		switch (at) {
			case "INTARRAY": return "INT";
			case "FLOATARRAY": return "FLOAT";
			case "BOOLARRAY": return "BOOL";
		}
		return at;
	}
}
