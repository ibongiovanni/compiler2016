package ir.ast;

import ir.ASTVisitor;
import java.util.LinkedList;
import java.util.List;

public class MethodDecl extends Declaration {
	protected String id;
	protected List<FormalParam> args;
	protected Body body;

	public MethodDecl(Type t, String id, Body body){
		setType(t);
		this.id = id;
		this.args = new LinkedList<FormalParam>();
		this.body = body;
	}

	public MethodDecl(Type t, String id, List<FormalParam> args, Body body){
		setType(t);
		this.id = id;
		this.args = args;
		this.body = body;
	}

	public String getId(){
		return id;
	}

	public  List<FormalParam> getArgs(){
		return args;
	}

	public Body getBody(){
		return body;
	}

	@Override
	public String toString() {
		String rep;
		rep = type.toString()+" "+id+"(";
		for ( FormalParam a : args ) {
			rep = rep+" "+a.toString()+",";
		} //Sobra una coma al final
		rep = rep+") "+body.toString();
		return rep;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}