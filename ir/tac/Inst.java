package ir.tac;

public enum Inst{

	LCINT ("LC-INT"), //load constant ["LC"|C|-|T1]
	LCFLT ("LC-FLT"),
	LCBOOL ("LC-BOOL"),
	LMINT ("LM-INT"), //load from memory
	LMFLT ("LM-FLT"),
	LMBOOL ("LM-BOOL"),
	LMARRINT ("LM-ARR-INT"), //load from array
	LMARRFLT ("LM-ARR-FLT"), //('position'|'array'|null)
	LMARRBOOL ("LM-ARR-BOOL"),

	CMP ("CMP"), //Compare
	//Jumps
	JMP ("JMP"), 	//jump
	JF ("JF"), 		//jump if false
	JZ ("JZ"),		//jump if zero
	JNZ ("JNZ"),	//jump if not zero
	JE ("JE"),		//jump if equals
	JNE ("JNE"), 	//jump if not equals
	JL ("JL"), 	//jump if less
	JG ("JG"),
	JGE ("JGE"),

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

	AND ("AND"),
	OR ("OR"),
	NOT ("NOT"),

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