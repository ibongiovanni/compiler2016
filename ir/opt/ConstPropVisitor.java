package ir.opt;

import java.util.ArrayList;
import java.util.List;

import ir.ASTVisitor;
import ir.ast.*;
import error.Error; // define class error


// type checker, concrete visitor 
public class ConstPropVisitor implements ASTVisitor<IntLiteral> {


	public ConstPropVisitor(){
	}

	// Visit declarations
	@Override
	public IntLiteral visit(Program dec){
		List<ClassDecl> classes=dec.getClasses();
		for ( ClassDecl c : classes ) {
			c.accept(this);
		}
		return null;
	}

	@Override
	public IntLiteral visit(ClassDecl dec){
		// List<FieldDecl> fields= dec.getFields();
		// for ( FieldDecl f : fields ) {
		// 	f.accept(this);
		// }
		List<MethodDecl> methods = dec.getMethods();
		for ( MethodDecl m : methods ) {
			m.accept(this);
		}
		return null;
	}
	
	
	@Override
	public IntLiteral visit(FieldDecl dec){
		List<VarDecl> elements = dec.getElements();
		for ( VarDecl v : elements ) {
 			v.accept(this);
 		}
		return null;
	}
	
	
	@Override
	public IntLiteral visit(MethodDecl dec){
		// List<FormalParam> args = dec.getArgs();
		// for ( FormalParam a : args ) {
		// 	a.accept(this);
		// } 
		dec.getBody().accept(this);
		return null;
	}
	
	
	@Override
	public IntLiteral visit(VarDecl dec){
		return null;
	}
	
		
	@Override
	public IntLiteral visit(ArrayDecl dec){
		return null;
	}
	
		
	@Override
	public IntLiteral visit(FormalParam dec){
		return null;
	}
	
	
	@Override
	public IntLiteral visit(Body dec){
		if (!dec.isExtern()) {
			dec.getBlock().accept(this);
		}
		return null;
	}
	
	

// visit statements
	@Override
	public IntLiteral visit(AssignStmt stmt){
		IntLiteral exRes = stmt.getExpression().accept(this);
		if (exRes!= null) {
			stmt.setExpression(exRes);
		}
		Location loc = stmt.getLocation();
		if (loc instanceof ArrayLocation) {
			((ArrayLocation)loc).accept(this);
		}
		return null;
	}
	
	
	@Override
	public IntLiteral visit(ReturnStmt stmt){
		Expression expr = stmt.getExpression();
		if (expr!=null) {
			IntLiteral exRes = expr.accept(this);
			if (exRes!=null) {
				stmt.setExpression(exRes);
			}
		}
		return null;
	}
	
	
	@Override
	public IntLiteral visit(IfStmt stmt){
		stmt.getCondition().accept(this);
		stmt.getIfStmt().accept(this);
		Statement elseStmt = stmt.getElseStmt();
		if (elseStmt!=null) {
			elseStmt.accept(this);
		}
		return null;
	}
	
	
	@Override
	public IntLiteral visit(BreakStmt stmt){	
		return null;
	}
	
	
	@Override
	public IntLiteral visit(ContinueStmt stmt){
		return null;
	}
	
	
	@Override
	public IntLiteral visit(EmptyStmt stmt){
		return null;
	}
	
	
	@Override
	public IntLiteral visit(ForStmt stmt){
		IntLiteral e1 = stmt.getExpr1().accept(this);
		if (e1!=null) {
			stmt.setExpr1(e1);
		}
		IntLiteral e2 = stmt.getExpr2().accept(this);
		if (e2!=null) {
			stmt.setExpr2(e1);
		}
		stmt.getBody().accept(this);
		return null;
	}
	
	
	@Override
	public IntLiteral visit(WhileStmt stmt){
		stmt.getCondition().accept(this);
		stmt.getBody().accept(this);
		return null;
	}
	
	
	@Override
	public IntLiteral visit(MethodCallStmt stmt){
		stmt.getCall().accept(this);
		return null;
	}
	
	
	@Override
	public IntLiteral visit(Block stmt){
		
		List<Statement> statements = stmt.getStatements();
	    for (Statement s: statements) {
			s.accept(this);
		}
		return null;
	}
	
	

// visit expressions
	private void travelBinExpr(BinOpExpr expr){
		IntLiteral op1 = expr.getLeftOperand().accept(this);
		if (op1!=null) {
			expr.setLeftOperand(op1);
		}
		IntLiteral op2 = expr.getRightOperand().accept(this);
		if (op2 != null) {
			expr.setRightOperand(op2);
		}
	}

	@Override
	public IntLiteral visit(BinAndExpr expr){
		travelBinExpr(expr);
		return null;
	}
	
	
	@Override
	public IntLiteral visit(BinDivideExpr expr){
		IntLiteral res =  null;
		IntLiteral op1 = expr.getLeftOperand().accept(this);
		IntLiteral op2 = expr.getRightOperand().accept(this);
		if (op1!=null && op2!=null) {
			int val1 = op1.getValue();
			int val2 = op2.getValue();
			res = new IntLiteral(val1/val2);
		}
		return res;
	}
	
	
	@Override
	public IntLiteral visit(BinEqualExpr expr){
		travelBinExpr(expr);
		return null;
	}
	
	
	@Override
	public IntLiteral visit(BinGOETExpr expr){
		travelBinExpr(expr);
		return null;
	}
	
	
	@Override
	public IntLiteral visit(BinGTExpr expr){
		travelBinExpr(expr);
		return null;
	}
	
	
	@Override
	public IntLiteral visit(BinLOETExpr expr){
		travelBinExpr(expr);
		return null;
	}
	
	
	@Override
	public IntLiteral visit(BinLTExpr expr){
		travelBinExpr(expr);
		return null;
	}
	
	
	@Override
	public IntLiteral visit(BinMinusExpr expr){
		IntLiteral res =  null;
		IntLiteral op1 = expr.getLeftOperand().accept(this);
		IntLiteral op2 = expr.getRightOperand().accept(this);
		if (op1!=null && op2!=null) {
			int val1 = op1.getValue();
			int val2 = op2.getValue();
			res = new IntLiteral(val1-val2);
		}
		return res;
	}
	
	
	@Override
	public IntLiteral visit(BinModExpr expr){
		IntLiteral res =  null;
		IntLiteral op1 = expr.getLeftOperand().accept(this);
		IntLiteral op2 = expr.getRightOperand().accept(this);
		if (op1!=null && op2!=null) {
			int val1 = op1.getValue();
			int val2 = op2.getValue();
			res = new IntLiteral(val1%val2);
		}
		return res;
	}
	
	
	@Override
	public IntLiteral visit(BinNotEqualExpr expr){
		travelBinExpr(expr);
		return null;
	}
	
	
	@Override
	public IntLiteral visit(BinOpExpr expr){
		travelBinExpr(expr);
		return null;
	}
	
	
	@Override
	public IntLiteral visit(BinOrExpr expr){
		travelBinExpr(expr);
		return null;
	}
	
	
	@Override
	public IntLiteral visit(BinPlusExpr expr){
		IntLiteral res =  null;
		IntLiteral op1 = expr.getLeftOperand().accept(this);
		IntLiteral op2 = expr.getRightOperand().accept(this);
		if (op1!=null && op2!=null) {
			int val1 = op1.getValue();
			int val2 = op2.getValue();
			res = new IntLiteral(val1+val2);
		}
		return res;
	}
	
	
	@Override
	public IntLiteral visit(BinTimesExpr expr){
		IntLiteral res =  null;
		IntLiteral op1 = expr.getLeftOperand().accept(this);
		IntLiteral op2 = expr.getRightOperand().accept(this);
		if (op1!=null && op2!=null) {
			int val1 = op1.getValue();
			int val2 = op2.getValue();
			res = new IntLiteral(val1*val2);
		}
		return res;
	}
	
	
	@Override
	public IntLiteral visit(BracketExpr expr){
		IntLiteral exRes = expr.getExpression().accept(this);
		return exRes;
	}
	
	
	@Override
	public IntLiteral visit(MethodCallExpr expr){
		List<Expression> params=expr.getParams();
		for ( Expression p : params ) {
			p.accept(this);
		}
		return null;
	}
	
	
	@Override
	public IntLiteral visit(MinusExpr expr){
		IntLiteral res =  null;
		IntLiteral op1 = expr.getExpression().accept(this);
		if (op1!=null) {
			int val1 = op1.getValue();
			res = new IntLiteral(-val1);
		}
		return res;
	}
	
		
	@Override
	public IntLiteral visit(NegatedExpr expr){
		expr.getExpression().accept(this);
		return null;
	}
	
	
	
// visit literals	
	@Override
	public IntLiteral visit(IntLiteral lit){
		return lit;
	}
	
	
	@Override
	public IntLiteral visit(FloatLiteral lit){
		return null;
	}
	
	
	@Override
	public IntLiteral visit(BoolLiteral lit){
		return null;
	}
	
	

// visit locations	
	@Override
	public IntLiteral visit(VarLocation loc){
		return null;
	}
	
	
	@Override
	public IntLiteral visit(SubClassVarLocation loc){
		return null;
	}
	
	
	@Override
	public IntLiteral visit(ArrayLocation loc){
		Expression index = loc.getIndex();
		IntLiteral res = index.accept(this);
		if (res != null) {
			loc.setIndex(res);
		}
		return null;
	}
	
	
	@Override
	public IntLiteral visit(SubClassArrayLocation loc){
		((ArrayLocation)loc).accept(this);
		return null;
	}
	
	


}