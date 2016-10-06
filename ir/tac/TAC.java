package ir.tac;

import ir.ast.*;

public class TAC {
	private Inst inst;
	private Object op1; //Can be an AST or a plane String
	private AST op2;
	private VarDecl res; //When a result is returned, it is stored in a VarDecl

	public TAC(Inst inst, Object op1, AST op2, VarDecl res){
		this.inst = inst;
		this.op1 = op1;
		this.op2 = op2;
		this.res = res;
	}

	public Inst getInst(){
		return inst;
	}

	public Object getOp1(){ return op1; }
	public AST getOp2(){ return op2; }

	public VarDecl getRes(){
		return res;
	}

	@Override
	public String toString(){
		String rep=  "[ "+inst+" \t| ";
		rep += (op1==null)? ("- | "):(op1+" | ");
		rep += (op2==null)? ("- | "):(op2+" | ");
		rep += (res==null)? ("- ]"):(res+" ]");
		return rep;
	}
}