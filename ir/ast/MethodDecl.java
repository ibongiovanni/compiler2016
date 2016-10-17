package ir.ast;

import ir.ASTVisitor;
import java.util.LinkedList;
import java.util.List;

public class MethodDecl extends NamedDecl {
	protected List<FormalParam> args;
	protected Body body;
	protected boolean isExt;

	public MethodDecl(String t, String id, Body body){
		setType(t);
		this.id = id;
		this.args = new LinkedList<FormalParam>();
		this.body = body;
	}

	public MethodDecl(String t, String id, List<FormalParam> args, Body body){
		setType(t);
		this.id = id;
		this.args = args;
		this.body = body;
	}

	public  List<FormalParam> getArgs(){
		return args;
	}

	public Body getBody(){
		return body;
	}

	public void setExternal(){
		isExt=true;
	}

	public boolean isExternal(){
		return isExt;
	}

	@Override
	public String toString() {
		/*String rep;
		rep = type.toString()+" "+id+"(";
		for ( FormalParam a : args ) {
			rep = rep+" "+a.toString()+",";
		} //Sobra una coma al final
		rep = rep+") "+body.toString();
		return rep;*/
		return id;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}