package ir.semcheck;

import java.util.ArrayList;
import java.util.List;

import ir.ASTVisitor;
import ir.ast.*;
import error.Error; // define class error


// type checker, concrete visitor 
public class TypeCheckVisitor implements ASTVisitor<String> {
	
	private List<Error> errors;

	private void addError(AST a, String desc) {
		errors.add(new Error(a.getLineNumber(), a.getColumnNumber(), desc));
	}

	public List<Error> getErrors() {
		return errors;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}
	

	@Override
	public String visit(Program dec){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(ClassDecl dec){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(FieldDecl dec){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(MethodDecl dec){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(VarDecl dec){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(ArrayDecl dec){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(FormalParam dec){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(Body dec){
		return Type.UNDEFINED;
	}


// visit statements
	@Override
	public String visit(AssignStmt stmt) {
		return Type.UNDEFINED;
	}

	@Override
	public String visit(ReturnStmt stmt){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(IfStmt stmt){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(BreakStmt stmt){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(ContinueStmt stmt){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(EmptyStmt stmt){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(ForStmt stmt){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(WhileStmt stmt){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(MethodCallStmt stmt){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(Block stmt){
		return Type.UNDEFINED;
	}


// visit expressions
	@Override
	public String visit(BinOpExpr expr) {
		return Type.UNDEFINED;
	}

	@Override
	public String visit(BinAndExpr expr){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(BinDivideExpr expr){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(BinEqualExpr expr){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(BinGOETExpr expr){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(BinGTExpr expr){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(BinLOETExpr expr){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(BinLTExpr expr){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(BinMinusExpr expr){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(BinModExpr expr){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(BinNotEqualExpr expr){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(BinOrExpr expr){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(BinPlusExpr expr){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(BinTimesExpr expr){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(BracketExpr expr){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(MethodCallExpr expr){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(MinusExpr expr){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(NegatedExpr expr){
		return Type.UNDEFINED;
	}

	
// visit literals	
	@Override
	public String visit(IntLiteral lit){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(FloatLiteral lit){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(BoolLiteral lit){
		return Type.UNDEFINED;
	}


// visit locations	
	@Override
	public String visit(VarLocation loc){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(SubClassVarLocation loc){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(ArrayLocation loc){
		return Type.UNDEFINED;
	}

	@Override
	public String visit(SubClassArrayLocation loc){
		return Type.UNDEFINED;
	}



	
}
