package compiler;

import java.util.LinkedList;
import java.util.List;

import java_cup.runtime.*;
import ir.ast.*;
import ir.ASTVisitor;
import ir.semcheck.*;

public class JavaParser {
	
	public static void print(Program prog){
		ASTVisitor<String> pv = new PrintVisitor();
		String programRep="== PROGRAM REPRESENTATION ==\n\n";
		programRep += pv.visit(prog)+"\n\n";
		System.out.println(programRep);
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

		print((Program) s.value);

    }
}