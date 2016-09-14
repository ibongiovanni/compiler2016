package ir.ast;

import ir.ASTVisitor;

import java.util.LinkedList;
import java.util.List;

public class Program extends AST {
	protected List<ClassDecl> classes;

	public Program(List<ClassDecl> cls){
		classes = cls;
	}

	public List<ClassDecl> getClasses(){
		return classes;
	}

	@Override
	public String toString() {
		String rep="";
		for ( ClassDecl c : classes ) {
			rep += c.toString()+"\n\n";
		}
		return rep;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}