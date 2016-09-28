package ir.semcheck;

import java.util.LinkedList;
import java.util.List;

import ir.tac.*;
import ir.ASTVisitor;
import ir.ast.*;
import error.Error; // define class error

public class TACVisitor implements ASTVisitor<VarDecl> {

	private List<TAC> instls; //Three-Adress-Code instructions list
	private boolean addInst(Inst inst, AST op1, AST op2, VarDecl res){
		return instls.add(new TAC(inst,op1,op2,res));
	}

	/***********************************************************************
	*	temporaly variables management
	*/
	private int temps;
	private VarDecl newTemp(){
		temps++;
		return new VarDecl("t"+temps);
	}

	/***********************************************************************/


	/***********************************************************************
	*	Error management
	*/
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
	/***********************************************************************/

	public TACVisitor(){
		errors = new LinkedList<Error>();
		instls = new LinkedList<TAC>();
	}

	/***********************************************************************
	*	Visit Declarations
	*/
	@Override
	public VarDecl visit(Program dec){
		return new VarDecl("null");
	}

	@Override
	public VarDecl visit(ClassDecl dec){
		return new VarDecl("null");
	}
	
	@Override
	public VarDecl visit(FieldDecl dec){
		return new VarDecl("null");
	}
	
	@Override
	public VarDecl visit(MethodDecl dec){
		return new VarDecl("null");
	}
	
	@Override
	public VarDecl visit(VarDecl dec){
		return new VarDecl("null");
	}
		
	@Override
	public VarDecl visit(ArrayDecl dec){
		return new VarDecl("null");
	}
		
	@Override
	public VarDecl visit(FormalParam dec){
		return new VarDecl("null");
	}
	
	@Override
	public VarDecl visit(Body dec){
		return new VarDecl("null");
	}
	

// visit statements
	@Override
	public VarDecl visit(AssignStmt stmt){
		return new VarDecl("null");
	}
	
	@Override
	public VarDecl visit(ReturnStmt stmt){
		return new VarDecl("null");
	}
	
	@Override
	public VarDecl visit(IfStmt stmt){
		return new VarDecl("null");
	}
	
	@Override
	public VarDecl visit(BreakStmt stmt){
		return new VarDecl("null");
	}
	
	@Override
	public VarDecl visit(ContinueStmt stmt){
		return new VarDecl("null");
	}
	
	@Override
	public VarDecl visit(EmptyStmt stmt){
		return new VarDecl("null");
	}
	
	@Override
	public VarDecl visit(ForStmt stmt){
		return new VarDecl("null");
	}
	
	@Override
	public VarDecl visit(WhileStmt stmt){
		return new VarDecl("null");
	}
	
	@Override
	public VarDecl visit(MethodCallStmt stmt){
		return new VarDecl("null");
	}
	
	@Override
	public VarDecl visit(Block stmt){
		return new VarDecl("null");
	}
	

// visit expressions

//Arithmetical Expressions
	@Override
	public VarDecl visit(BinPlusExpr expr){
		VarDecl op1 = expr.getLeftOperand().accept(this);
		VarDecl op2 = expr.getRightOperand().accept(this);
		VarDecl res = newTemp();
		switch (op1.getType()) {
			case "INT": addInst(Inst.PLUSINT,op1,op2,res); break;
			case "FLT": addInst(Inst.PLUSFLT,op1,op2,res); break;
		}
		return res;
	}
	
	@Override
	public VarDecl visit(BinMinusExpr expr){
		VarDecl op1 = expr.getLeftOperand().accept(this);
		VarDecl op2 = expr.getRightOperand().accept(this);
		VarDecl res = newTemp();
		switch (op1.getType()) {
			case "INT": addInst(Inst.MINUSINT,op1,op2,res); break;
			case "FLT": addInst(Inst.MINUSFLT,op1,op2,res); break;
		}
		return res;
	}
	
	@Override
	public VarDecl visit(BinTimesExpr expr){
		VarDecl op1 = expr.getLeftOperand().accept(this);
		VarDecl op2 = expr.getRightOperand().accept(this);
		VarDecl res = newTemp();
		switch (op1.getType()) {
			case "INT": addInst(Inst.TIMESINT,op1,op2,res); break;
			case "FLT": addInst(Inst.TIMESFLT,op1,op2,res); break;
		}
		return res;
	}
	
	@Override
	public VarDecl visit(BinDivideExpr expr){
		VarDecl op1 = expr.getLeftOperand().accept(this);
		VarDecl op2 = expr.getRightOperand().accept(this);
		VarDecl res = newTemp();
		switch (op1.getType()) {
			case "INT": addInst(Inst.DIVIDEINT,op1,op2,res); break;
			case "FLT": addInst(Inst.DIVIDEFLT,op1,op2,res); break;
		}
		return res;
	}
	
	@Override
	public VarDecl visit(BinModExpr expr){
		VarDecl op1 = expr.getLeftOperand().accept(this);
		VarDecl op2 = expr.getRightOperand().accept(this);
		VarDecl res = newTemp();
		switch (op1.getType()) {
			case "INT": addInst(Inst.MODINT,op1,op2,res); break;
			case "FLT": addInst(Inst.MODFLT,op1,op2,res); break;
		}
		return res;
	}

//Relational Expressions
	@Override
	public VarDecl visit(BinGTExpr expr){
		VarDecl op1 = expr.getLeftOperand().accept(this);
		VarDecl op2 = expr.getRightOperand().accept(this);
		VarDecl res = newTemp();
		switch (op1.getType()) {
			case "INT": addInst(Inst.GTINT,op1,op2,res); break;
			case "FLT": addInst(Inst.GTFLT,op1,op2,res); break;
		}
		return res;
	}
	
	@Override
	public VarDecl visit(BinGOETExpr expr){
		VarDecl op1 = expr.getLeftOperand().accept(this);
		VarDecl op2 = expr.getRightOperand().accept(this);
		VarDecl res = newTemp();
		switch (op1.getType()) {
			case "INT": addInst(Inst.GOETINT,op1,op2,res); break;
			case "FLT": addInst(Inst.GOETFLT,op1,op2,res); break;
		}
		return res;
	}
	
	@Override
	public VarDecl visit(BinLTExpr expr){
		VarDecl op1 = expr.getLeftOperand().accept(this);
		VarDecl op2 = expr.getRightOperand().accept(this);
		VarDecl res = newTemp();
		switch (op1.getType()) {
			case "INT": addInst(Inst.LTINT,op1,op2,res); break;
			case "FLT": addInst(Inst.LTFLT,op1,op2,res); break;
		}
		return res;
	}

	@Override
	public VarDecl visit(BinLOETExpr expr){
		VarDecl op1 = expr.getLeftOperand().accept(this);
		VarDecl op2 = expr.getRightOperand().accept(this);
		VarDecl res = newTemp();
		switch (op1.getType()) {
			case "INT": addInst(Inst.LOETINT,op1,op2,res); break;
			case "FLT": addInst(Inst.LOETFLT,op1,op2,res); break;
		}
		return res;
	}
	
//Comparison Expressions
	@Override
	public VarDecl visit(BinEqualExpr expr){
		VarDecl op1 = expr.getLeftOperand().accept(this);
		VarDecl op2 = expr.getRightOperand().accept(this);
		VarDecl res = newTemp();
		switch (op1.getType()) {
			case "INT": addInst(Inst.EQINT,op1,op2,res); break;
			case "FLT": addInst(Inst.EQFLT,op1,op2,res); break;
			case "BOOL": addInst(Inst.EQBOOL,op1,op2,res); break;
		}
		return res;
	}
	
	@Override
	public VarDecl visit(BinNotEqualExpr expr){
		VarDecl op1 = expr.getLeftOperand().accept(this);
		VarDecl op2 = expr.getRightOperand().accept(this);
		VarDecl res = newTemp();
		switch (op1.getType()) {
			case "INT": addInst(Inst.NEQINT,op1,op2,res); break;
			case "FLT": addInst(Inst.NEQFLT,op1,op2,res); break;
			case "BOOL": addInst(Inst.NEQBOOL,op1,op2,res); break;
		}
		return res;
	}

//Conditional Expressions	
	@Override
	public VarDecl visit(BinAndExpr expr){
		VarDecl op1 = expr.getLeftOperand().accept(this);
		VarDecl op2 = expr.getRightOperand().accept(this);
		VarDecl res = newTemp();
		addInst(Inst.AND,op1,op2,res);
		return res;
	}

	@Override
	public VarDecl visit(BinOrExpr expr){
		VarDecl op1 = expr.getLeftOperand().accept(this);
		VarDecl op2 = expr.getRightOperand().accept(this);
		VarDecl res = newTemp();
		addInst(Inst.OR,op1,op2,res);
		return res;
	}
	
	@Override
	public VarDecl visit(BinOpExpr expr){
		VarDecl op1 = expr.getLeftOperand().accept(this);
		VarDecl op2 = expr.getRightOperand().accept(this);
		VarDecl res = newTemp();
		addInst(null,op1,op2,res);
		return res;
	}
	
	
	@Override
	public VarDecl visit(MinusExpr expr){
		VarDecl op1 = expr.getExpression().accept(this);
		VarDecl res = newTemp();
		switch (op1.getType()) {
			case "INT": addInst(Inst.UMINUSINT,op1,null,res); break;
			case "FLT": addInst(Inst.UMINUSFLT,op1,null,res); break;
		}
		return res;
	}
		
	@Override
	public VarDecl visit(NegatedExpr expr){
		VarDecl op1 = expr.getExpression().accept(this);
		VarDecl res = newTemp();
		addInst(Inst.NOT,op1,null,res);
		return res;
	}
	
	@Override
	public VarDecl visit(BracketExpr expr){
		return expr.getExpression().accept(this);
	}
	
	@Override
	public VarDecl visit(MethodCallExpr expr){
		return new VarDecl("null");
	}
	
	
	
// visit literals	
	@Override
	public VarDecl visit(IntLiteral lit){
		VarDecl res = newTemp();
		addInst(Inst.LCINT,lit,null,res);
		return res;
	}
	
	@Override
	public VarDecl visit(FloatLiteral lit){
		VarDecl res = newTemp();
		addInst(Inst.LCFLT,lit,null,res);
		return res;
	}
	
	@Override
	public VarDecl visit(BoolLiteral lit){
		VarDecl res = newTemp();
		addInst(Inst.LCBOOL,lit,null,res);
		return res;
	}
	

// visit locations	
	@Override
	public VarDecl visit(VarLocation loc){
		return new VarDecl("null");
	}
	
	@Override
	public VarDecl visit(SubClassVarLocation loc){
		return new VarDecl("null");
	}
	
	@Override
	public VarDecl visit(ArrayLocation loc){
		return new VarDecl("null");
	}
	
	@Override
	public VarDecl visit(SubClassArrayLocation loc){
		return new VarDecl("null");
	}
	
	
}