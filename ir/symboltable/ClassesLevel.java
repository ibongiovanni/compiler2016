package ir.symboltable;

import java.util.LinkedList;
import java.util.List;
import ir.ast.*;

public class ClassesLevel extends TableLevel {
	private List<ClassDecl> classes;

	public ClassesLevel(){
		classes= new LinkedList<ClassDecl>();
	}

	public boolean addClass(ClassDecl c){
		ClassDecl aux = searchClass (c.getId());
		return (aux != null)?  classes.add(c) : false;
	}

	public ClassDecl searchClass(String c){
		ClassDecl res =null;
		for (int i=0; i<classes.size() && res==null ; i++ ) {
			if (c.equals(classes.get(i).getId())) {
				res = classes.get(i);
			}
		}
		return res;
	}
}