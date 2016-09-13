package ir.ast;

import ir.ASTVisitor;

import java.util.LinkedList;
import java.util.List;

public class ClassDecl extends AST {
	private String id;
	private List<FieldDecl> fields;
	private List<MethodDecl> methods;

	public ClassDecl(String id){
		this.id = id;
		this.fields = new LinkedList<FieldDecl>();
		this.methods = new LinkedList<MethodDecl>();
	}

	/*public ClassDecl(String id, List<FieldDecl> fields){
		this.id = id;
		this.fields = fields;
		this.methods = new LinkedList<MethodDecl>();
	}

	public ClassDecl(String id, List<MethodDecl> methods){
		this.id = id;
		this.fields = new LinkedList<FieldDecl>();
		this.methods = methods;
	}*/

	public ClassDecl(String id, List<FieldDecl> fields, List<MethodDecl> methods){
		this.id = id;
		if (fields==null) {
			this.fields = new LinkedList<FieldDecl>();
		}
		else {
			this.fields = fields;
		}
		if (methods==null) {
			this.methods = new LinkedList<MethodDecl>();		
		}
		else {
			this.methods = methods;	
		}
	}

	public String getId(){ return id; }
	public List<FieldDecl> getFields(){ return fields; }
	public List<MethodDecl> getMethods() { return methods; }

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