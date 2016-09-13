package ir.ast;

import ir.ASTVisitor;
import java.util.LinkedList;
import java.util.List;

public class FieldDecl extends Declaration {
	protected List<VarDecl> elements;

	public FieldDecl(Type t, List<VarDecl> elements){
		setType(t);
		this.elements = elements;
		assignTypes(); 
	}

	//Assign its corresponding type to each var declared;
	private void assignTypes(){
		for ( VarDecl v : elements ) {
			if (!(v instanceof ArrayDecl)) {
				v.setType(type);
			}
			else{
				v.setType(toArrayType(type));
			}
		}
	}

	private Type toArrayType(Type t){
		switch (t) {
			case INT:
				return Type.INTARRAY;
			case FLOAT:
				return Type.FLOATARRAY;
			case BOOL:
				return Type.BOOLARRAY;
			}
		return Type.UNDEFINED;
	}

	public List<VarDecl> getElements(){
		return elements;
	}

	@Override
	public String toString() {
		String rep;
		rep = type.toString()+" ";
 		for ( VarDecl v : elements ) {
 			rep = rep+" "+v.toString()+",";
 		}
 		//Sobra una coma al final
 		rep=rep+";";
		return rep;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}