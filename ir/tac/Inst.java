package ir.tac;

public enum Inst{

	LCINT ("LCINT"), //load constant ["LC"|C|-|T1]
	LCFLT ("LCFLT"),
	LCBOOL ("LCBOOL"),
	LMINT ("LMINT"), //load from memory
	LMFLT ("LMFLT"),
	LMBOOL ("LMBOOL"),

	CMP ("CMP"), //Compare
	//Jumps
	JMP ("JMP"), 	//jump
	JZ ("JZ"),		//jump if zero
	JNZ ("JNZ"),	//jump if not zero
	JE ("JE"),		//jump if equals
	JNE ("JNE"), 	//jump if not equals
	JL ("JL"), 	//jump if less

	ASSIGN ("ASSIGN"), //Assignment

	PLUSINT ("PLUSINT"),
	PLUSFLT ("PLUSFLT"),
	MINUSINT ("MINUSINT"),
	MINUSFLT ("MINUSFLT"),
	UMINUSINT ("UMINUSINT"),
	UMINUSFLT ("UMINUSFLT"),
	TIMESINT ("TIMESINT"),
	TIMESFLT ("TIMESFLT"),
	DIVIDEINT ("DIVIDEINT"),
	DIVIDEFLT ("DIVIDEFLT"),
	MODINT ("MODINT"),
	MODFLT ("MODFLT"),

	AND ("AND"),
	OR ("OR"),
	NOT ("NOT"),

	EQINT ("EQINT"),
	EQFLT ("EQFLT"),
	EQBOOL ("EQBOOL"),
	NEQINT ("NEQINT"),
	NEQFLT ("NEQFLT"),
	NEQBOOL ("NEQBOOL"),

	LTINT ("LTINT"),
	LTFLT ("LTFLT"),
	LOETINT ("LOETINT"),
	LOETFLT ("LOETFLT"),
	GTINT ("GTINT"),
	GTFLT ("GTFLT"),
	GOETINT ("GOETINT"),
	GOETFLT ("GOETFLT")
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