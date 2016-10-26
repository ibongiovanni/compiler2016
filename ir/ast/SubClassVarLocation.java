package ir.ast;

import ir.ASTVisitor;
import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;

public class SubClassVarLocation extends VarLocation {
	private List<String> classes;
	private String[] splitted;
	protected VarDecl objRef; //Reference to object

	public SubClassVarLocation(String calls){
		splitted = calls.split("[.]");
		classes = new LinkedList<String> (Arrays.asList(splitted));
		this.id = classes.remove(classes.size()-1);
	}

	public List<String> getClasses(){
		return classes;
	}

	public void setObjRef(VarDecl cd){
		objRef=cd;
	}

	public VarDecl getObjRef(){
		return objRef;
	}

	@Override
	public String toString() {
		String rep="";
		for (String c : classes ) {
			rep=rep+c+".";
		}
		return rep+id;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
	
}