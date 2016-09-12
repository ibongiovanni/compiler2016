package ir.ast;

import ir.ASTVisitor;
import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;

public class SubClassArrayLocation extends ArrayLocation {
	private List<String> classes;
	private String[] splitted;

	public SubClassArrayLocation(String calls, Expression ind){
		splitted = calls.split(".");	//Split string into array
		classes = new LinkedList<String> (Arrays.asList(splitted));	//transform array into list
		this.id = classes.remove(classes.size()-1);	//Remove array id from list
		index = ind;		
		this.blockId = -1;
	}

	@Override
	public String toString() {
		String rep="";
		for (String c : classes ) {
			rep=rep+c+".";
		}
		return rep+id+"["+index+"]";
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
	
}