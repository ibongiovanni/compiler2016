package ir.symboltable;

import java.util.LinkedList;
import java.util.List;
import ir.ast.*;

public class MethodArgsLevel extends TableLevel {
	List<FormalParam> args;

	public MethodArgsLevel(){
		args = new LinkedList<FormalParam>();
	}

	public FormalParam searchArg(String n){
		FormalParam has=null;
		for (int i = 0; has==null && i<args.size(); i++ ) {
			if ( n.equals( args.get(i).getId() ) ) {
				has=args.get(i);
			}
		}
		return has;
	}

	public boolean addArg(FormalParam a){
		return (searchArg(a.getId())==null)? args.add(a) : false;
	}
}