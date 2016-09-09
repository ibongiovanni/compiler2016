package ir.ast;

import ir.ASTVisitor;

import java.util.LinkedList;
import java.util.List;

public class ClassDecl extends AST {
	private String id;
	private List<FieldDecl> fields;
	private List<MethodDecl> methods;

	public ClassDecl(String id, List<FieldDecl> fields, List<MethodDecl> methods){
		this.id = id;
		this.fields = fields;
		this.methods = methods;
	}

	public String getId(){ return id; }

	@Override
	public String toString() {
		String rep;
		rep = "Class "+id+" {\n";
		for ( FieldDecl f : fields ) {
			rep = rep+"\t"+f.toString()+"\n";
		}
		for ( MethodDecl m : methods ) {
			rep = rep+"\t"+m.toString()+"\n";
		}
		rep = rep+"}";
		return rep;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}