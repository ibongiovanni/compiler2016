package ir.symboltable;

import java.util.LinkedList;
import java.util.List;
import ir.ast.*;

public class SymbolTable {

	private List<TableLevel> table;
	private TableLevel top;
	private List<VarLocation> publics; 	//Each method and attrib decl is 'published' for other classes
										//Each location is identified as "classname.identifier"
	
	public SymbolTable(){
		table = new LinkedList<TableLevel>();
		top = null;
		publics = new LinkedList<VarLocation>();
	}

	public void newLevel(AST caller){
		if (caller instanceof Program ) {
			table.add(0,new ClassesLevel());
		}
		if (caller instanceof ClassDecl ) {
			//System.out.println("New ClassLocalDeclLevel"); //elim
			table.add(0,new ClassLocalDeclLevel((ClassDecl)caller));
		}
		if (caller instanceof MethodDecl ) {
			table.add(0,new MethodArgsLevel());
		}
		if (caller instanceof Block ) {
			table.add(0,new BlockLevel((Block)caller));
		}
		top = table.get(0);
	} //hace falta un metodo para cada tipo?

	public void closeLevel(){
		table.remove(0);
		top = table.get(0);
	}

	public void insertId(AST id){ //(String id, Type t)

	}

	public boolean addClass(String id){
		ClassesLevel aux = (ClassesLevel)table.get(table.size()-1); //¿Podria castear top,es seguro?
		return aux.addClass(id);
	}

	public boolean addAttr(VarDecl a){
		if (top instanceof ClassLocalDeclLevel) {
			ClassLocalDeclLevel aux = (ClassLocalDeclLevel) top;
			boolean added = aux.addAttr(a);
			if (added) {

				//publish it in the publics list
				VarLocation loc = new VarLocation(aux.getName()+"."+a.getId());
				loc.setRef(a);
				publics.add(loc);
			}
			else {
				System.out.println("not added attrib"); //elim
			}
			return added;
		}
		else {
			System.out.println("top is not an instanceof ClassLocalDeclLevel"); //elim
			return false;
		}
	}

	public boolean addMethod(MethodDecl m){
		if (top instanceof ClassLocalDeclLevel) {
			ClassLocalDeclLevel aux = (ClassLocalDeclLevel) top;
			boolean added = aux.addMethod(m);
			if (added) {
				//publish it in the publics list
				VarLocation loc = new VarLocation(aux.getName()+"."+m.getId());
				loc.setRef(m);
				publics.add(loc);
			}
			return added;
		}
		else {
			return false;
		}
	}

	public boolean addArg(FormalParam a){
		if (top instanceof MethodArgsLevel) {
			MethodArgsLevel aux = (MethodArgsLevel) top;
			return aux.addArg(a);
		}
		return false;
	}

	public boolean addVar(VarDecl v){
		if (top instanceof BlockLevel) {
			BlockLevel aux = (BlockLevel)top;
			return aux.addVar(v);
		}
		else if (top instanceof ClassLocalDeclLevel) {
			return addAttr(v);
		}
		System.out.println("top neither a block nor ClassLocalDeclLevel"); //elim
		return false;
	}

	public boolean searchInMethodScope(){
		return true;
	}

	public AST searchId(String id){
		TableLevel aux;
		AST result=null;
		for (int i=0 ; i<table.size()-1 && result==null; i++ ) {
			aux=table.get(i);
			if (aux instanceof BlockLevel) {
				result = ((BlockLevel)aux).searchVar(id);
			}
			if (aux instanceof MethodArgsLevel) {
				result = ((MethodArgsLevel)aux).searchArg(id);
			}
			if (aux instanceof ClassLocalDeclLevel) {
				result = ((ClassLocalDeclLevel)aux).searchAttr(id);
			}
		}
		return result;
	}

	public MethodDecl searchMethod(String id){
		TableLevel aux;
		MethodDecl result=null;
		for (int i=0 ; i<table.size()-1 && result==null; i++ ) {
			aux=table.get(i);
			if (aux instanceof ClassLocalDeclLevel) {
				result = ((ClassLocalDeclLevel)aux).searchMethod(id);
			}
		}
		return result;
	}

	public VarLocation searchPublic(String cl, String id){
		VarLocation has=null;
		String n = cl+"."+id;
		for (int i = 0; has==null && i<publics.size(); i++ ) {
			if ( n.equals( publics.get(i).getId() ) ) {
				has=publics.get(i);
			}
		}
		return has;
	}

	public boolean searchClass(String c){
		if (top instanceof ClassesLevel) {
			ClassesLevel aux = (ClassesLevel)top;
			return aux.searchClass(c); 
		}
		else {
			ClassesLevel aux = (ClassesLevel)table.get(table.size()-1);
			return aux.searchClass(c);
		}
	}
}