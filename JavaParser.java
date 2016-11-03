package compiler;

import java.util.LinkedList;
import java.util.List;

import java_cup.runtime.*;
import ir.ast.*;
import ir.ASTVisitor;
import ir.semcheck.*;
import ir.tac.*;
import error.Error;
import asm.*;
import ir.opt.*;

public class JavaParser {
	
	private static List<TAC> tacls;

	public static void genAsm(String fpath){
		AsmGen gen = new AsmGen(tacls,fpath);
		gen.run();
	}

	public static void print(Program prog){
		ASTVisitor<String> pv = new PrintVisitor();
		String programRep="\n== PROGRAM REPRESENTATION ==\n\n";
		programRep += pv.visit(prog)+"\n\n";
		System.out.println(programRep);
	}

	public static boolean build(Program prog){
		BuilderVisitor bv = new BuilderVisitor();
		System.out.println("\n=== Building References ===\n");
		if (prog.accept(bv)) {
			System.out.println(" -Succesfuly Checked References");
			return true;
		}
		else {
			List<Error> errors = bv.getErrors();
			for ( Error e : errors ) {
				System.out.println(e.toString());
			}
			return false;
		}
	}

	public static void taclist(Program prog){
		TACVisitor tacv = new TACVisitor();
		System.out.println("\n=== TAC List ===\n");
		prog.accept(tacv);
		tacls = tacv.getList();
		for ( TAC tac : tacls ) {
			System.out.println(tac);
		}
	}

	public static boolean checkTypes(Program prog){
		System.out.println("\n=== Checking Types ===\n");
		TypeCheckVisitor tv = new TypeCheckVisitor();
		prog.accept(tv);
		List<Error> errors = tv.getErrors();
		if (errors.isEmpty()) {
			System.out.println(" -Succesfuly Checked Types");
			return true;
		}
		else {
		 	for ( Error e : errors ) {
		 		System.out.println(e.toString());
		 	}
		 	return false;
		 } 
	}

	public static void constProp(Program prog){
		System.out.println("\n=== Optimizing Contants Propagation ===\n");
		ConstPropVisitor cpv = new ConstPropVisitor();
		prog.accept(cpv);
		System.out.println("\n RESULT OF OPTIMIZATION \n");
		print(prog);
	}

	public static void main(String args[]) throws Exception {
		ComplexSymbolFactory sf = new ComplexSymbolFactory();
		Parser par;
		java_cup.runtime.Symbol s;
		if (args.length==0) {
			par = new Parser(new Scanner(System.in,sf),sf);
		}
		else {
			par = new Parser(new Scanner(new java.io.FileInputStream(args[0]),sf),sf);
		}
		s=par.parse();
		System.out.println("\n\nParsing ended");

		Program prog = (Program) s.value; 
		print(prog);
		if(build(prog)){
			if (checkTypes(prog)) {
				//Optimization 1, constant propagation
				constProp(prog);

				taclist(prog);
				genAsm(args[0]);
			}
		}
    }
}