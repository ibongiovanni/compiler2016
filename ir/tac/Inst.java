package ir.tac;

public enum Inst{

	LCINT ("LC-INT"), //load constant ["LC"|C|-|T1]
	LCFLT ("LC-FLT"),
	LCBOOL ("LC-BOOL"),
	LMINT ("LM-INT"), //load from memory
	LMFLT ("LM-FLT"), //('var'|'null'|'res')
	LMBOOL ("LM-BOOL"),
	LMARRINT ("LM-ARR-INT"), //load from array
	LMARRFLT ("LM-ARR-FLT"), //('position'|'array'|'res')
	LMARRBOOL ("LM-ARR-BOOL"),

	CMP ("CMP\t"), //Compare
	//Jumps
	JMP ("JMP\t"), 	//jump
	JF ("JF\t"), 		//jump if false
	JZ ("JZ\t"),		//jump if zero
	JNZ ("JNZ\t"),	//jump if not zero
	JE ("JE\t"),		//jump if equals
	JNE ("JNE\t"), 	//jump if not equals
	JL ("JLv"), 	//jump if less
	JG ("JG\t"),
	JGE ("JGE\t"),

	ASSIGN ("ASSIGN"), //Assignment
	RETURN ("RETURN"),
	NULLRETURN ("NULL-RETURN"),

	BREAK ("BREAK"),
	CONTINUE ("CONTINUE"),

	ADDCONST ("ADD-CONST"),
	PLUSINT ("PLUS-INT"),
	PLUSFLT ("PLUS-FLT"),
	MINUSINT ("MINUS-INT"),
	MINUSFLT ("MINUS-FLT"),
	UMINUSINT ("UMINUS-INT"),
	UMINUSFLT ("UMINUS-FLT"),
	TIMESINT ("TIMES-INT"),
	TIMESFLT ("TIMES-FLT"),
	DIVIDEINT ("DIVIDE-INT"),
	DIVIDEFLT ("DIVIDE-FLT"),
	MODINT ("MOD-INT"),
	MODFLT ("MOD-FLT"),

	AND ("AND\t"),
	OR ("OR\t"),
	NOT ("NOT\t"),

	EQINT ("EQ-INT"),
	EQFLT ("EQ-FLT"),
	EQBOOL ("EQ-BOOL"),
	NEQINT ("NEQ-INT"),
	NEQFLT ("NEQ-FLT"),
	NEQBOOL ("NEQ-BOOL"),

	LTINT ("LT-INT"),
	LTFLT ("LT-FLT"),
	LOETINT ("LOET-INT"),
	LOETFLT ("LOET-FLT"),
	GTINT ("GT-INT"),
	GTFLT ("GT-FLT"),
	GOETINT ("GOET-INT"),
	GOETFLT ("GOET-FLT"),


	LABEL ("LABEL"),

	PROGRAMINIT ("PROGRAM-INIT"),
	PROGRAMEND ("PROGRAM-END"),
	
	CLASSINIT ("CLASS-INIT"),
	CLASSEND ("CLASS-END"),

	METHODINIT ("METHOD-INIT"),
	METHODEND ("METHOD-END"),
	EXTERNBODY ("EXTERN-BODY"),
	CALLEXPR ("CALL-EXPR"),
	CALLSTMT ("CALL-STMT"),
	ARGUMENT ("ARGUMENT"),

	DECVARINT ("DEC-VAR-INT"),
	DECVARFLT ("DEC-VAR-FLT"),
	DECVARBOOL ("DEC-VAR-BOOL"),
	DECVARINTARRAY ("DEC-ARR-INT"),
	DECVARFLTARRAY ("DEC-ARR-FLT"),
	DECVARBOOLARRAY ("DEC-ARR-BOOL")


;

	private String name;

	private Inst(String value){
		name=value;
	}

	@Override
	public String toString(){
		return name;
	}
}