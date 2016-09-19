package ir.symboltable;

import java.util.LinkedList;
import java.util.List;
import ir.ast.*;

public class BlockLevel extends TableLevel {
	private List<VarDecl> locals;

	public BlockLevel(Block dec){
		locals = new LinkedList<VarDecl>();
		// List<FieldDecl> fields = dec.getFields();
		// for ( FieldDecl f : fields ) {
		// 	locals.addAll(f.getElements()); // Extract every var decl from each field declarated
		// }
	}

	public VarDecl searchVar(String n){
		VarDecl has=null;
		for (int i = 0; has==null && i<locals.size(); i++ ) {
			if ( n.equals( locals.get(i).getId() ) ) {
				has=locals.get(i);
			}
		}
		return has;
	}

	public boolean addVar(VarDecl v){
		return (searchVar(v.getId())==null)? locals.add(v) : false;
	}

}