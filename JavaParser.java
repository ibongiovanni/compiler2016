package compiler;

import java.util.LinkedList;
import java.util.List;

import java_cup.runtime.*;
import ir.ast.*;
import ir.ASTVisitor;
import ir.semcheck.*;
import error.Error;

public class JavaParser {
	
	public static void print(Program prog){
		ASTVisitor<String> pv = new PrintVisitor();
		String programRep="== PROGRAM REPRESENTATION ==\n\n";
		programRep += pv.visit(prog)+"\n\n";
		System.out.println(programRep);
	}

	public static void build(Program prog){
		BuilderVisitor bv = new BuilderVisitor();
		System.out.println("=== Building References ===\n");
		if (prog.accept(bv)) {
			System.out.println(" -Succesfuly Checked References");
		}
		else {
			List<Error> errors = bv.getErrors();
			for ( Error e : errors ) {
				System.out.println(e.toString());
			}
		}
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
		build(prog);

    }
}