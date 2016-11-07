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
	String fName;


	public AsmGen(List<TAC> list,String fpath){
		this.list =list;
		String sep = File.separator; 				//Get system-dependent separator ('/' in unix)
		String[] paths = fpath.split("["+sep+"]"); 	//Disect path in directories and filename
		String nam = paths[paths.length-1]; 		//get last element (the filename)
		fName = nam; 								//nam is the filename with extension
		try{
			String fnNoExt = nam.split("[.]")[0]; 	//get filename without extension
			file = new File("out"+sep+fnNoExt+".s");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			System.out.println("\nFile created at: "+file.getAbsolutePath());
			fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
		}
		catch (IOException e) {
			e.printStackTrace();
		}


	}

	private void write(String s){
		//Indent string
		for (int i=0; i<indentLvl ;i++) {
			s="\t"+s;
		}
		//try to write the line
		try{
			bw.write(s);
			bw.newLine();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	/********* Indentation ***********/

	private int indentLvl;
	private void incIndent(){
		indentLvl++;
	}
	private void decIndent(){
		indentLvl--;
	}
 	/*********************************/

 	/*********** Location of elements ***********/

 	private String varLoc(VarDecl v, VarDecl ae){
 		String res;
 		if (v.isAtt()) {
 			res = attribLoc(v,ae);
 		}
 		//Local Variable
 		else{
 			if (v instanceof ArrayDecl) {
 				res = arrayLoc((ArrayDecl)v,ae);
 			}
 			else {
 				int vOff = v.getOffset();
 				res = "-"+vOff+"(%rbp)";
 			}
 		}
 		return res;
 	}

	private String arrayLoc(ArrayDecl al, VarDecl e){
		int word = 8;
		int arrOff = al.getOffset();
		int exprOff = e.getOffset();
		//move expression to rax
		write("mov -"+exprOff+"(%rbp), %rax");
		checkBounds(al);
		return "-"+arrOff+"(%rbp,%rax,"+word+")";

	}

	private String attribLoc(VarDecl att, VarDecl ae){
		int word = 8;
		if (att.isStc()) {
			String id = att.getId();
			if (ae==null) {
				return id+"(%rip)";
			}
			else{
				int aeOff = ae.getOffset();
				//move array offset to rax
				write("mov -"+aeOff+"(%rbp), %rax");
				checkBounds((ArrayDecl)att);
				return id+"(,%rax,"+word+")";
			}
		}
		else{	
			int pos = att.getAttPos();
			if (ae != null) {
				//move array offset to current offset
				int aeOff = ae.getOffset();
				write("mov -"+aeOff+"(%rbp), %rax");
				checkBounds((ArrayDecl)att);
				//add position to rax
				write("add $"+pos+", %rax");
			}
			else{
				//Move position to rax
				write("mov $"+pos+", %rax");
			}
			return "(%rcx,%rax,"+word+")";
		}
	}
	/**************************************************/

	/***************** Bound Checking *****************/
	private int checkNmbr;
	private void checkBounds(ArrayDecl ar){
		write("# check array bounds");
		incIndent();
		//Check if greater of equal to zero
		write("cmp $0, %rax");
		write("jge almost_alright"+checkNmbr);
		write("call alert");
		decIndent();
		write("almost_alright"+checkNmbr+":");

		incIndent();
		//Check if less than array size
		int size = ar.getSize();
		write("cmp $"+size+", %rax");
		write("jl alright"+checkNmbr);
		write("call alert");
		decIndent();
		write("alright"+checkNmbr+":");
		write("# end of check array bounds");
		checkNmbr++;
	}
	/**************************************************/


	public void run(){
		List<TAC> toDel = new LinkedList<TAC>();
		for ( TAC tac : list ) {
			switch (tac.getInst()) {
				case DECSTCATT: {
					decStcAtt(tac);
					toDel.add(tac);
					break;
				}
			}
		}
		//Remove static attributes declarations from list
		list.removeAll(toDel);

		for ( TAC tac : list ) {
			write("# "+tac);
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
				case DECOBJ 	: decObj(tac); break;
				case LOBJ 	: lObj(tac); break;
			}
			write("");
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
		// mov $lit, -off(%rbp)
		String ord;
		IntLiteral lit = (IntLiteral)tac.getOp1();
		int val = lit.getValue();
		int off = tac.getRes().getOffset();
		ord = "movq $"+val+", -"+off+"(%rbp)";
		write(ord);
	}

	private void loadConstFlt(TAC tac){
		// mov $lit, -off(%rbp)
		String ord;
		FloatLiteral lit = (FloatLiteral)tac.getOp1();
		float val = lit.getValue();
		int off = tac.getRes().getOffset();
		ord = "mov $"+val+", -"+off+"(%rbp)";
		write(ord);
	}

	private void loadConstBool(TAC tac){
		String ord;
		BoolLiteral lit = (BoolLiteral)tac.getOp1();
		int val = (lit.getValue())? 1:0; //1=true 0=false
		int off = tac.getRes().getOffset();
		ord = "movq $"+val+", -"+off+"(%rbp)";
		write(ord);
	}

/** Load Vars */
	private void loadMemInt(TAC tac){
		String ord;
		VarLocation vl = (VarLocation)tac.getOp1();
		VarDecl vd = (VarDecl)vl.getRef();
		if (vd.isAtt()) {
			String attOff = attribLoc(vd,null);
			//move att to r10
			write("mov "+attOff+", %r10");
		}
		else {
			int varOff = vd.getOffset();	
			//mov var to r10
			write("mov -"+varOff+"(%rbp), %r10");
		}
		
		int tempOff = tac.getRes().getOffset();
		//move r10 to mem
		write("mov %r10, -"+tempOff+"(%rbp)");
	}

	private void loadMemFlt(TAC tac){

	}

	private void loadMemBool(TAC tac){
		loadMemInt(tac);
	}

/** Load Objects */
	private void lObj(TAC tac){
		Location ol = (Location)tac.getOp1();
		VarDecl obj;
		if (ol instanceof SubClassVarLocation) {
			obj = ((SubClassVarLocation)ol).getObjRef();
		}
		else{
			obj = ((SubClassArrayLocation)ol).getObjRef();
		}
		int objOff = obj.getOffset();
		//Load object's effective address in rcx
		write("leaq -"+objOff+"(%rbp), %rcx");
	}

/** Load Arrays */
	private void loadArrInt(TAC tac){
		int tempOff = tac.getRes().getOffset();
		ArrayLocation al = (ArrayLocation)tac.getOp2();
		VarDecl ad = (VarDecl)al.getRef();
		VarDecl arExpr = (VarDecl)tac.getOp1();
		if (ad.isAtt()) {
			String attOff = attribLoc(ad,arExpr);
			//move att to r10
			write("mov "+attOff+", %r10");
		}
		else{
			String arDir = arrayLoc((ArrayDecl)al.getRef(),arExpr);
			//move var to r10
			write("mov "+arDir+", %r10");
		}

		//move r10 to mem
		write("mov %r10, -"+tempOff+"(%rbp)");
	}

	private void loadArrFlt(TAC tac){

	}

	private void loadArrBool(TAC tac){
		loadArrInt(tac);
	}

/** Conditional and Jumps */
	private void cmp(TAC tac){
		//move fisrt operand to r10
		int op1Off = ((VarDecl)tac.getOp1()).getOffset();
		String op1Loc = varLoc((VarDecl)tac.getOp1(),null);
		write("mov "+op1Loc+", %r10");

		//move second operand to r11
		int op2Off = ((VarDecl)tac.getOp2()).getOffset();
		String op2Loc = varLoc((VarDecl)tac.getOp2(),null);
		write("mov "+op2Loc+", %r11");
		
		//compare them
		write("cmp %r11, %r10");
	}
	
	private void jmp(TAC tac){
		String lbl = (String)tac.getOp1();
		write("jmp "+lbl);
	}
	
	private void jf(TAC tac){
		//Get label
		String lbl = (String)tac.getOp1();

		//get offset of the result of evalutaion
		int op2Off = ((VarDecl)tac.getOp2()).getOffset();
		String op2Loc = varLoc((VarDecl)tac.getOp2(),null);
		write("mov "+op2Loc+", %r11");

		//compare result to 0
		write("cmp $0, %r11");
		//jump if they are equals;
		write("je "+lbl);
	}

	private void jge(TAC tac){
		String lbl = (String)tac.getOp1();
		write("jge "+lbl);
	}

/** Assignment and Return Stmts */
	private void assign(TAC tac){
		//get expression offset
		int eOff = ((VarDecl)tac.getOp1()).getOffset();
		String eLoc = varLoc((VarDecl)tac.getOp1(),null);
		//move expr to r10
		write("mov "+eLoc+", %r10");

		VarDecl dec = (VarDecl)tac.getRes();
		VarDecl ae=null;
		if(tac.getOp2() != null){
			ae = (VarDecl)tac.getOp2();
		}
		String loc = varLoc(dec,ae);
		//move r10 content to memory
		write("mov %r10, "+loc);	
		
		/*if (!(dec instanceof ArrayDecl)) {
			if (dec.isAtt()) {
				String attOff = attribLoc(dec,null);
				//move r10 content to memory
				write("mov %r10, "+attOff);
			}
			else{
				//get location offset
				int locOff = dec.getOffset();
				//move r10 content to memory
				write("mov %r10, -"+locOff+"(%rbp)");
			}
		}
		if (dec instanceof ArrayDecl) {
			if(dec.isAtt()){
				String attOff = attribLoc(dec,(VarDecl)tac.getOp2());
				//move r10 content to memory
				write("mov %r10, "+attOff);
			}
			else{	
				String arrOff = arrayLoc((ArrayDecl)tac.getRes(), (VarDecl)tac.getOp2());
				//move r10 content to memory
				write("mov %r10, "+arrOff);
			}
		}*/
	}

	private void retExpr(TAC tac){
		//get result offset
		int resOff = tac.getRes().getOffset();
		String resLoc = varLoc(tac.getRes(),(VarDecl)tac.getOp2());

		//move result to rax
		write("mov "+resLoc+", %rax");
	}

	private void retVoid(TAC tac){

	}

/** Break and Continue */
	private void breakStmt(TAC tac){
		jmp(tac);
	}

	private void contStmt(TAC tac){
		jmp(tac);
	}

/** Arithmetical Operations */
	private void addConst(TAC tac){
		//get result offset
		int resOff = tac.getRes().getOffset();

		//move res to r10
		write("mov -"+resOff+"(%rbp), %r10");

		//increment r10
		write("inc %r10");

		//move r10 back to mem
		write("mov %r10, -"+resOff+"(%rbp)");
	}

	private void plusInt(TAC tac){
		//get op1 offset
		String op1Loc = varLoc((VarDecl)tac.getOp1(),null);
		//get op2 offset
		String op2Loc = varLoc((VarDecl)tac.getOp2(),null);
		//get result offset
		String resLoc = varLoc(tac.getRes(),null);

		//move op1 to r10
		write("mov "+op1Loc+", %r10");

		//move op2 to r11
		write("mov "+op2Loc+", %r11");

		//perform add over r10
		write("add %r10, %r11");

		//move result to mem
		write("mov %r11, "+resLoc);
	}

	private void plusFlt(TAC tac){

	}

	private void minusInt(TAC tac){
		//get op1 offset
		String op1Loc = varLoc((VarDecl)tac.getOp1(),null);
		//get op2 offset
		String op2Loc = varLoc((VarDecl)tac.getOp2(),null);
		//get result offset
		String resLoc = varLoc(tac.getRes(),null);

		//move op1 to r11
		write("mov "+op1Loc+", %r11");

		//move op2 to r10
		write("mov "+op2Loc+", %r10");

		//perform sub over r11
		write("sub %r10, %r11");

		//move result to mem
		write("mov %r11, "+resLoc);

	}

	private void minusFlt(TAC tac){

	}

	private void uMinusInt(TAC tac){
		//get op1 offset
		String op1Loc = varLoc((VarDecl)tac.getOp1(),null);
		//get result offset
		String resLoc = varLoc(tac.getRes(),null);
		
		//move op1 to r10
		write("mov "+op1Loc+", %r10");

		//perform negation over r10
		write("neg %r10");

		//move result to mem
		write("mov %r10, "+resLoc);
	}

	private void uMinusFlt(TAC tac){

	}

	private void timesInt(TAC tac){
		//get op1 offset
		String op1Loc = varLoc((VarDecl)tac.getOp1(),null);
		//get op2 offset
		String op2Loc = varLoc((VarDecl)tac.getOp2(),null);
		//get result offset
		String resLoc = varLoc(tac.getRes(),null);

		//move op1 to r10
		write("mov "+op1Loc+", %r10");

		//move op2 to r11
		write("mov "+op2Loc+", %r11");

		//perform multiply over r10
		write("imul %r11, %r10");

		//move result to mem
		write("mov %r10, "+resLoc);
	}

	private void timesFlt(TAC tac){

	}

	private void divideInt(TAC tac){
		//Division is op1/op2
		//get op1 offset
		String op1Loc = varLoc((VarDecl)tac.getOp1(),null);
		//get op2 offset
		String op2Loc = varLoc((VarDecl)tac.getOp2(),null);
		//get result offset
		String resLoc = varLoc(tac.getRes(),null);

		//clear rdx
		write("xor %rdx, %rdx");

		//move op1 (dividend) to rax
		write("mov "+op1Loc+", %rax");

		//perform division over rax
		write("idivq "+op2Loc);

		//move quotient to mem
		write("mov %rax, "+resLoc);

	}

	private void divideFlt(TAC tac){

	}

	private void modInt(TAC tac){
		//get op1 offset
		String op1Loc = varLoc((VarDecl)tac.getOp1(),null);
		//get op2 offset
		String op2Loc = varLoc((VarDecl)tac.getOp2(),null);
		//get result offset
		String resLoc = varLoc(tac.getRes(),null);

		//clear rdx
		write("xor %rdx, %rdx");

		//move op1 (dividend) to rax
		write("mov "+op1Loc+", %rax");

		//perform division over rax
		write("idivq "+op2Loc);

		//move quotient to mem
		write("mov %rdx, "+resLoc);
	}

	private void modFlt(TAC tac){

	}

/** Logical Operations */
	private void auxInst(TAC tac, String inst){
		//get op1 offset
		String op1Loc = varLoc((VarDecl)tac.getOp1(),null);
		//get op2 offset
		String op2Loc = varLoc((VarDecl)tac.getOp2(),null);
		//get result offset
		String resLoc = varLoc(tac.getRes(),null);

		//move op1 to r10
		write("mov "+op1Loc+", %r10");

		//move op2 to r11
		write("mov "+op2Loc+", %r11");

		//perform inst over r10
		write(inst+" %r10, %r11");

		//move result to mem
		write("mov %r11, "+resLoc);
	}

	private void and(TAC tac){
		auxInst(tac,"and");
		// //get op1 offset
		// int op1Off = ((VarDecl)tac.getOp1()).getOffset();
		// //get op2 offset
		// int op2Off = ((VarDecl)tac.getOp2()).getOffset();
		// //get result offset
		// int resOff = tac.getRes().getOffset();

		// //move op1 to r10
		// write("mov -"+op1Off+"(%rbp), %r10");
		// //move op2 to r11
		// write("mov -"+op2Off+"(%rbp), %r11");
		// write("mov $0, %r12");

		// write("cmp %r10, %r11");
		// write("cmovne %r12, %r10"); //if op1<>op2 -> op1&&op2=false

		// //move result to mem
		// write("mov %r10, -"+resOff+"(%rbp)");

	}

	private void or(TAC tac){
		auxInst(tac,"or");
		// //get op1 offset
		// int op1Off = ((VarDecl)tac.getOp1()).getOffset();
		// //get op2 offset
		// int op2Off = ((VarDecl)tac.getOp2()).getOffset();
		// //get result offset
		// int resOff = tac.getRes().getOffset();

		// //move op1 to r10
		// write("mov -"+op1Off+"(%rbp), %r10");
		// //move op2 to r11
		// write("mov -"+op2Off+"(%rbp), %r11");
		// write("mov $1, %r12");

		// write("cmp %r10, %r11");
		// write("cmovne %r12, %r10"); //if op1<>op2 -> op1||op2=true

		// //move result to mem
		// write("mov %r10, -"+resOff+"(%rbp)");
	}

	private void not(TAC tac){
		//get op1 offset
		String op1Loc = varLoc((VarDecl)tac.getOp1(),null);
		//get result offset
		String resLoc = varLoc(tac.getRes(),null);

		//move op1 to r10
		write("mov "+op1Loc+", %r10");

		//move 1 to r11
		write("mov $1, %r11");

		//compare them
		write("cmp %r10, %r11");

		//set register r11 to return value
		write("mov $0, %r11");
		write("mov $1, %r10");
		write("cmovne %r10, %r11");

		//move result to mem
		write("mov %r11, "+resLoc);

	}

/** Comparison Operations */
	private void compAux(TAC tac, String cc){
		//get op1 offset
		String op1Loc = varLoc((VarDecl)tac.getOp1(),null);
		//get op2 offset
		String op2Loc = varLoc((VarDecl)tac.getOp2(),null);
		//get result offset
		String resLoc = varLoc(tac.getRes(),null);

		//move op1 to r10
		write("mov "+op1Loc+", %r10");

		//move op2 to r11
		write("mov "+op2Loc+", %r11");

		//compare them
		write("cmp %r11, %r10");

		//set register r11 to return value
		write("mov $0, %r11");
		write("mov $1, %r10");
		write("cmov"+cc+" %r10, %r11");

		//move result to mem
		write("mov %r11, "+resLoc);
	}

	private void eqInt(TAC tac){
		compAux(tac,"e");
	}

	private void eqFlt(TAC tac){

	}

	private void eqBool(TAC tac){
		eqInt(tac);
	}

	private void neqInt(TAC tac){
		compAux(tac,"ne");
	}

	private void neqFlt(TAC tac){

	}

	private void neqBool(TAC tac){
		neqInt(tac);
	}

/** Realational Operations */
	private void ltInt(TAC tac){
		compAux(tac,"l");
	}

	private void ltFlt(TAC tac){

	}

	private void loetInt(TAC tac){
		compAux(tac,"le");
	}

	private void loetFlt(TAC tac){

	}

	private void gtInt(TAC tac){
		compAux(tac,"g");
	}

	private void gtFlt(TAC tac){

	}

	private void goetInt(TAC tac){
		compAux(tac,"ge");
	}

	private void goetFlt(TAC tac){

	}

/** Labels */
	private void label(TAC tac){
		String lbl = (String)tac.getOp1();
		// String t = lbl.substring(0,3);
		// if (t.equals("End")) {
		// 	decIndent();
		// }
		write(lbl+":");
		// if (t.equals("Ini")) {
		// 	incIndent();
		// }
	}

	private void progInit(TAC tac){
		write(".file \""+fName+"\"");
		write(".text");
	}

	private void progEnd(TAC tac){
		write("#program end");
	}

	private void classInit(TAC tac){
		String cl = (String)tac.getOp1();
		write("#class "+cl+" init");
	}

	private void classEnd(TAC tac){
		write("#class end");
	}

	private void methodInit(TAC tac){
		String lbl = ((MethodDecl)tac.getOp1()).getId();
		incIndent();
		write(".globl "+lbl);
		write(".type "+lbl+", @function");
		decIndent();
		write(lbl+":");
		incIndent();
		int methOff = ((MethodDecl)tac.getOp1()).getOffset();
		write("enter $"+methOff+",$0");

		//move arguments to mem
		List<FormalParam> args = ((MethodDecl)tac.getOp1()).getArgs();
		int argsC = args.size();
		String[] regs = {"rdi","rsi","rdx","rcx","r8","r9"};
		for (int i=0; i<argsC && i<6; i++ ) {
			int argOff = args.get(i).getOffset();
			String argLoc = varLoc(args.get(i),null);
			write("mov %"+regs[i]+", "+argLoc);
		}
		if (argsC>6) {
			//????
		}

	}

	private void methodEnd(TAC tac){
		write("leave");
		write("ret\n");
		decIndent();
	}

	private void extern(TAC tac){

	}

/** Calls and Arguments */
	private void callExpr(TAC tac){
		//get method name
		String lbl = ((MethodDecl)tac.getOp1()).getId();
		//get result offset
		String resLoc = varLoc(tac.getRes(),null);

		int argsC = ((MethodDecl)tac.getOp1()).getArgs().size();
		//Move args to regs
		moveArgs(argsC);

		//make call
		write("call "+lbl);
		//move result from rax to mem
		write("mov %rax, "+resLoc);
	}

	private void callStmt(TAC tac){
		//get method name
		String lbl = ((MethodDecl)tac.getOp1()).getId();

		int argsC = ((MethodDecl)tac.getOp1()).getArgs().size();
		//Move args to regs
		moveArgs(argsC);

		//make call
		write("call "+lbl);
	}

	private void moveArgs(int n){
		for (int i=0; i<n ; i++ ) {
			write(argsLs.remove(0));
		}
		// for ( String inst : argsLs ) {
		// 	write(inst);
		// }
		// argsLs.clear();
	}

	private List<String> argsLs = new LinkedList<String>();

	private void argument(TAC tac){
		int n = (int)tac.getOp1();
		int argOff = ((VarDecl)tac.getOp2()).getOffset();
		String argLoc = varLoc((VarDecl)tac.getOp2(),null);
		String reg=null;
		switch (n) {
			case 1: reg="rdi"; break;
			case 2: reg="rsi"; break;
			case 3: reg="rdx"; break;
			case 4: reg="rcx"; break;
			case 5: reg="r8"; break;
			case 6: reg="r9"; break;
		}
		if (reg!=null) {
			//mov arg to reg
			argsLs.add(0,"mov "+argLoc+", %"+reg);
		}
	}

/** Declarations */
	private void decVarInt(TAC tac){
		//get var offset
		VarDecl vd = (VarDecl)tac.getOp1();
		int varOff = vd.getOffset();
		String vLoc = varLoc(vd,null);
		if (!vd.isAtt() && !(vd instanceof FormalParam)) {
			//initialize var with 0;
			write("movq $0, "+vLoc);
		}
		else if(!(vd instanceof FormalParam)){
				write("\t# attribute "+vd+" declared");
			}
	}

	private void decVarFlt(TAC tac){

	}

	private void decVarBool(TAC tac){
		decVarInt(tac);
	}

	private void decArrInt(TAC tac){

	}

	private void decArrFlt(TAC tac){

	}

	private void decArrBool(TAC tac){

	}

	private void decObj(TAC tac){
		VarDecl obj = (VarDecl)tac.getOp1();
		write("# object "+obj+" declared with size: "+obj.getClassDecl().getSize());
	}

	private void decStcAtt(TAC tac){
		VarDecl att = (VarDecl)tac.getOp1();
		String id = att.getId();
		int len = 8;
		if (att instanceof ArrayDecl) {
			len *= ((ArrayDecl)att).getSize();
		}
		write(".comm	"+id+","+len);
	}


}