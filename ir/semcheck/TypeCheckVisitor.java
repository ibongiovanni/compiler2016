package ir.semcheck;

import java.util.ArrayList;
import java.util.List;

import ir.ASTVisitor;
import ir.ast.*;
import error.Error; // define class error


// type checker, concrete visitor 
public class TypeCheckVisitor implements ASTVisitor<Type> {
	
	private List<Error> errors;

	@Override
	public Type visit(Program dec){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(ClassDecl dec){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(FieldDecl dec){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(MethodDecl dec){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(VarDecl dec){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(ArrayDecl dec){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(FormalParam dec){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(Body dec){
		return Type.UNDEFINED;
	}


// visit statements
	@Override
	public Type visit(AssignStmt stmt) {
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(ReturnStmt stmt){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(IfStmt stmt){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(BreakStmt stmt){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(ContinueStmt stmt){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(EmptyStmt stmt){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(ForStmt stmt){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(WhileStmt stmt){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(MethodCallStmt stmt){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(Block stmt){
		return Type.UNDEFINED;
	}


// visit expressions
	@Override
	public Type visit(BinOpExpr expr) {
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(BinAndExpr expr){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(BinDivideExpr expr){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(BinEqualExpr expr){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(BinGOETExpr expr){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(BinGTExpr expr){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(BinLOETExpr expr){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(BinLTExpr expr){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(BinMinusExpr expr){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(BinModExpr expr){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(BinNotEqualExpr expr){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(BinOrExpr expr){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(BinPlusExpr expr){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(BinTimesExpr expr){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(BracketExpr expr){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(MethodCallExpr expr){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(MinusExpr expr){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(NegatedExpr expr){
		return Type.UNDEFINED;
	}

	
// visit literals	
	@Override
	public Type visit(IntLiteral lit){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(FloatLiteral lit){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(BoolLiteral lit){
		return Type.UNDEFINED;
	}


// visit locations	
	@Override
	public Type visit(VarLocation loc){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(SubClassVarLocation loc){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(ArrayLocation loc){
		return Type.UNDEFINED;
	}

	@Override
	public Type visit(SubClassArrayLocation loc){
		return Type.UNDEFINED;
	}



	private void addError(AST a, String desc) {
		errors.add(new Error(a.getLineNumber(), a.getColumnNumber(), desc));
	}

	public List<Error> getErrors() {
		return errors;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}
}
