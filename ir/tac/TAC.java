package ir.tac;

import ir.ast.*;

public class TAC {
	private Inst inst;
	private AST op1;
	private AST op2;
	private VarDecl res;

	public TAC(Inst inst, AST op1, AST op2, VarDecl res){
		this.inst = inst;
		this.op1 = op1;
		this.op2 = op2;
		this.res = res;
	}

	public VarDecl getRes(){
		return res;
	}

	@Override
	public String toString(){
		return "[ "+inst+" | "+op1+" | "+op2+" | "+res+" ]";
	}
}