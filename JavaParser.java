package compiler;

import java.io.*;
import java_cup.runtime.*;

public class JavaParser {
	public static void main(String args[]) throws Exception {
		ComplexSymbolFactory sf = new ComplexSymbolFactory();
		if (args.length==0) {
			new Parser(new Scanner(System.in,sf),sf).parse();
		}
		else {
			new Parser(new Scanner(new java.io.FileInputStream(args[0]),sf),sf).parse();
		}

		System.out.println("\n\nParsing ended");
    }
}