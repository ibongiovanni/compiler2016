package ir.symboltable;

import java.util.LinkedList;
import java.util.List;
import ir.ast.*;

public class ClassesLevel extends TableLevel {
	private List<String> classes;

	public ClassesLevel(){
		classes= new LinkedList<String>();
	}

	public boolean addClass(String c){
		return (!classes.contains(c))?  classes.add(c) : false;
	}

	
}