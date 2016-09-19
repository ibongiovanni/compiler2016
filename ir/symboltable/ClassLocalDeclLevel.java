package ir.symboltable;

import java.util.LinkedList;
import java.util.List;
import ir.ast.*;

public class ClassLocalDeclLevel extends TableLevel {
	private List<VarDecl> attrs;
	private List<MethodDecl> methods;
	private String classname;

	public ClassLocalDeclLevel(ClassDecl dec){
		classname = dec.getId();
		attrs = new LinkedList<VarDecl>();
		methods = new LinkedList<MethodDecl>();

		// methods = dec.getMethods();
		// List<FieldDecl> fields = dec.getFields();
		// for ( FieldDecl f : fields ) {
		// 	attrs.addAll(f.getElements()); // Extract every var decl from each field declarated
		// }
	}

	public String getName(){
		return classname;
	}

	public VarDecl searchAttr(String n){
		VarDecl has=null;
		for (int i = 0; has==null && i<attrs.size(); i++ ) {
			if ( n.equals( attrs.get(i).getId() ) ) {
				has=attrs.get(i);
			}
		}
		return has;
	}

	public boolean addAttr(VarDecl a){
		if (searchAttr(a.getId())==null && searchMethod(a.getId())==null) {
			return attrs.add(a);
		}
		else {
			return false; //Repeated declaration
		}
	}

	public MethodDecl searchMethod(String n){
		MethodDecl has=null;
		for (int i = 0; has==null && i<methods.size(); i++ ) {
			if ( n.equals( methods.get(i).getId() ) ) {
				has=methods.get(i);
			}
		}
		return has;
	}

	public boolean addMethod(MethodDecl m){
		if (searchMethod(m.getId())==null && searchAttr(m.getId())==null) {
			return methods.add(m);
		}
		else {
			return false; //Repeated declaration
		}
	}


}