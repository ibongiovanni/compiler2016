package ir.ast;

import ir.ASTVisitor;
import java.util.LinkedList;
import java.util.List;

public class SubClassVarLocation extends VarLocation {
	private List<String> classes;
	private String[] splitted;

	public SubClassVarLocation(String calls){
		splitted = calls.split(".");
		classes = new LinkedList<String> (Arrays.asList(splitted));
		this.id = classes.remove(classes.size()-1);
	}

	@Override
	public String toString() {
		String rep;
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