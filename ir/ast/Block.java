package ir.ast;

import java.util.ArrayList;
import java.util.List;
import ir.ASTVisitor;

public class Block extends Statement {
	private List<FieldDecl> declarations;
	private List<Statement> statements;
	private int blockId;
	
	public Block(int bId) {
		declarations = new ArrayList<FieldDecl>();
		statements = new ArrayList<Statement>();
		blockId = bId;
	}

	/*public Block(int bId, List<FieldDecl> f) {
		blockId = bId;
		declarations = f;
		statements = new ArrayList<Statement>();
	}
	
	public Block(int bId, List<Statement> s) {
		blockId = bId;
		declarations = new ArrayList<FieldDecl>();
		statements = s;
	}*/

	public Block(int bId, List<FieldDecl> f, List<Statement> s) {
		blockId = bId;
		if (f==null) {
			declarations = new ArrayList<FieldDecl>();
		}
		else {
			declarations = f;
		}
		if (s==null) {
			statements = new ArrayList<Statement>();
		}
		else {
			statements = s;
		}
	}
	
	public void addStatement(Statement s) {
		this.statements.add(s);
	}

	public void addField(FieldDecl f) {
		this.declarations.add(f);
	}
		
	public List<Statement> getStatements() {
		return this.statements;
	} 

	public List<FieldDecl> getFields(){
		return declarations;
	}

	public int getBlockId() {
		return blockId;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}

	@Override
	public String toString() {
		String rtn = "{\n";
		for (FieldDecl f: declarations) {
			rtn += "\t"+f.toString()+"\n";
		}
	    for (Statement s: statements) {
			rtn += "\t"+s.toString() + '\n';
		}
		rtn += "}";
		
		return rtn; 
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
	
}
