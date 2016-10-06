package asm;

import ir.ast.*;
import ir.tac.*;

import java.util.LinkedList;
import java.util.List;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class AsmGen {
	
	private List<TAC> list;

	File file;
	FileWriter fw;
	BufferedWriter bw;


	public AsmGen(List<TAC> list){
		this.list =list;
		try{
			file = new File("program.asm");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
		}
		catch (IOException e) {
			e.printStackTrace();
		}


	}

	private void write(String s){
		try{
			bw.write(s);
			bw.newLine();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void run(){
		for ( TAC tac : list ) {
			switch (tac.getInst()) {
				case LCINT 	: loadConstInt(tac); break;
				case LCFLT 	: loadConstFlt(tac); break;
				case LCBOOL : loadConstBool(tac); break;
				case LMINT 	: loadMemInt(tac); break;
				case LMFLT 	: loadMemFlt(tac); break;
				case LMBOOL : loadMemBool(tac); break;
				case LMARRINT 	: loadArrInt(tac); break;
				case LMARRFLT 	: loadArrFlt(tac); break;
				case LMARRBOOL	: loadArrBool(tac); break;
				case CMP	: cmp(tac); break;
				case JMP	: jmp(tac); break;
				case JF		: jf(tac); break;
				case JGE	: jge(tac); break;
				case ASSIGN	: assign(tac); break;
				case RETURN	: retExpr(tac); break;
				case NULLRETURN	: retVoid(tac); break;
				case BREAK 	: breakStmt(tac); break;
				case CONTINUE	: contStmt(tac); break;
				case ADDCONST 	: addConst(tac); break;
				case PLUSINT 	: plusInt(tac); break;
				case PLUSFLT 	: plusFlt(tac); break;
				case MINUSINT 	: minusInt(tac); break;
				case MINUSFLT 	: minusFlt(tac); break;
				case UMINUSINT 	: uMinusInt(tac); break;
				case UMINUSFLT 	: uMinusFlt(tac); break;
				case TIMESINT 	: timesInt(tac); break;
				case TIMESFLT 	: timesFlt(tac); break;
				case DIVIDEINT 	: divideInt(tac); break;
				case DIVIDEFLT 	: divideFlt(tac); break;
				case MODINT 	: modInt(tac); break;
				case MODFLT 	: modFlt(tac); break;
				case AND 	: and(tac); break;
				case OR 	: or(tac); break;
				case NOT 	: not(tac); break;
				case EQINT 	: eqInt(tac); break;
				case EQFLT 	: eqFlt(tac); break;
				case EQBOOL 	: eqBool(tac); break;
				case NEQINT 	: neqInt(tac); break;
				case NEQFLT 	: neqFlt(tac); break;
				case NEQBOOL 	: neqBool(tac); break;
				case LTINT 	: ltInt(tac); break;
				case LTFLT 	: ltFlt(tac); break;
				case LOETINT 	: loetInt(tac); break;
				case LOETFLT 	: loetFlt(tac); break;
				case GTINT 	: gtInt(tac); break;
				case GTFLT 	: gtFlt(tac); break;
				case GOETINT 	: goetInt(tac); break;
				case GOETFLT 	: goetFlt(tac); break;
				case LABEL 	: label(tac); break;
				case PROGRAMINIT 	: progInit(tac); break;
				case PROGRAMEND 	: progEnd(tac); break;
				case CLASSINIT 	: classInit(tac); break;
				case CLASSEND 	: classEnd(tac); break;
				case METHODINIT 	: methodInit(tac); break;
				case METHODEND 	: methodEnd(tac); break;
				case EXTERNBODY 	: extern(tac); break;
				case CALLEXPR 	: callExpr(tac); break;
				case CALLSTMT 	: callStmt(tac); break;
				case ARGUMENT 	: argument(tac); break;
				case DECVARINT 	: decVarInt(tac); break;
				case DECVARFLT 	: decVarFlt(tac); break;
				case DECVARBOOL 	: decVarBool(tac); break;
				case DECVARINTARRAY 	: decArrInt(tac); break;
				case DECVARFLTARRAY 	: decArrFlt(tac); break;
				case DECVARBOOLARRAY 	: decArrBool(tac); break;
			}
		}
		try{
		    if(bw!=null)
				bw.close();
		}
		catch(Exception ex){
			System.out.println("Error in closing the BufferedWriter"+ex);
	    }
	}

/** Load Constansts */	
	private void loadConstInt(TAC tac){

	}

	private void loadConstFlt(TAC tac){

	}

	private void loadConstBool(TAC tac){

	}

/** Load Vars */
	private void loadMemInt(TAC tac){

	}

	private void loadMemFlt(TAC tac){

	}

	private void loadMemBool(TAC tac){

	}

/** Load Arrays */
	private void loadArrInt(TAC tac){

	}

	private void loadArrFlt(TAC tac){

	}

	private void loadArrBool(TAC tac){

	}

/** Conditional and Jumps */
	private void cmp(TAC tac){

	}
	
	private void jmp(TAC tac){
		
	}
	
	private void jf(TAC tac){
		
	}

	private void jge(TAC tac){
		
	}

/** Assignment and Return Stmts */
	private void assign(TAC tac){

	}

	private void retExpr(TAC tac){

	}

	private void retVoid(TAC tac){

	}

/** Break and Continue */
	private void breakStmt(TAC tac){

	}

	private void contStmt(TAC tac){

	}

/** Arithmetical Operations */
	private void addConst(TAC tac){

	}

	private void plusInt(TAC tac){

	}

	private void plusFlt(TAC tac){

	}

	private void minusInt(TAC tac){

	}

	private void minusFlt(TAC tac){

	}

	private void uMinusInt(TAC tac){

	}

	private void uMinusFlt(TAC tac){

	}

	private void timesInt(TAC tac){

	}

	private void timesFlt(TAC tac){

	}

	private void divideInt(TAC tac){

	}

	private void divideFlt(TAC tac){

	}

	private void modInt(TAC tac){

	}

	private void modFlt(TAC tac){

	}

/** Logical Operations */
	private void and(TAC tac){

	}

	private void or(TAC tac){

	}

	private void not(TAC tac){

	}

/** Comparison Operations */
	private void eqInt(TAC tac){

	}

	private void eqFlt(TAC tac){

	}

	private void eqBool(TAC tac){

	}

	private void neqInt(TAC tac){

	}

	private void neqFlt(TAC tac){

	}

	private void neqBool(TAC tac){

	}

/** Realational Operations */
	private void ltInt(TAC tac){

	}

	private void ltFlt(TAC tac){

	}

	private void loetInt(TAC tac){

	}

	private void loetFlt(TAC tac){

	}

	private void gtInt(TAC tac){

	}

	private void gtFlt(TAC tac){

	}

	private void goetInt(TAC tac){

	}

	private void goetFlt(TAC tac){

	}

/** Labels */
	private void label(TAC tac){

	}

	private void progInit(TAC tac){

	}

	private void progEnd(TAC tac){

	}

	private void classInit(TAC tac){

	}

	private void classEnd(TAC tac){

	}

	private void methodInit(TAC tac){

	}

	private void methodEnd(TAC tac){

	}

	private void extern(TAC tac){

	}

/** Calls and Arguments */
	private void callExpr(TAC tac){

	}

	private void callStmt(TAC tac){

	}

	private void argument(TAC tac){

	}

/** Declarations */
	private void decVarInt(TAC tac){

	}

	private void decVarFlt(TAC tac){

	}

	private void decVarBool(TAC tac){

	}

	private void decArrInt(TAC tac){

	}

	private void decArrFlt(TAC tac){

	}

	private void decArrBool(TAC tac){

	}


}