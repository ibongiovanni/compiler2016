package ir.semcheck;

import java.util.ArrayList;
import java.util.List;

import ir.ASTVisitor;
import ir.ast.*;
import error.Error; // define class error


// type checker, concrete visitor 
public class PrintVisitor implements ASTVisitor<String> {
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
	public String visit(ClassDecl dec){
		String rep;
		List<FieldDecl> fields= dec.getFields();
		List<MethodDecl> methods = dec.getMethods();
		rep = "Class "+dec.getId()+" {\n";
		for ( FieldDecl f : fields ) {
			rep = rep+"\t"+f.accept(this)+"\n";
		}
		for ( MethodDecl m : methods ) {
			rep = rep+"\t"+m.accept(this)+"\n";
		}
		rep = rep+"}";
		return rep;
	}

	@Override
	public String visit(FieldDecl dec){
		String rep;
		List<VarDecl> elements = dec.getElements();
		rep = dec.getType().toString()+" ";
 		for ( VarDecl v : elements ) {
 			rep += " "+v.accept(this)+",";
 		}
 		//Sobra una coma al final
 		rep=rep+";";
		return rep;
	}

	@Override
	public String visit(MethodDecl dec){
		String rep;
		List<FormalParam> args = dec.getArgs();
		Body body = dec.getBody();
		rep = dec.getType().toString()+" "+dec.getId()+"(";
		for ( FormalParam a : args ) {
			rep +=" "+a.accept(this)+",";
		} //Sobra una coma al final
		rep +=") "+body.accept(this);
		return rep;
	}

	@Override
	public String visit(VarDecl dec){
		return dec.toString();
	}

	@Override
	public String visit(ArrayDecl dec){
		return dec.toString();
	}

	@Override
	public String visit(FormalParam dec){
		return dec.toString();
	}

	@Override
	public String visit(Body dec){
		String rep;
		Block block = dec.getBlock();
		return (dec.isExtern())? "extern;" : block.accept(this);
	}


// visit statements
	@Override
	public String visit(AssignStmt stmt) {
		Location location=stmt.getLocation();
		Expression expr=stmt.getExpression();
		AssignOpType operator=stmt.getOperator();
		return location.accept(this) + " " + operator + " " + expr.accept(this)+";";
	}

	@Override
	public String visit(ReturnStmt stmt){
		String rep="return";
		Expression expression = stmt.getExpression();
		return (expression==null)? rep+";" : rep+" "+expression.accept(this)+";";
	}

	@Override
	public String visit(IfStmt stmt){
		String rep;
		Expression condition = stmt.getCondition();
		Statement ifStmt = stmt.getIfStmt();
		Statement elseStmt = stmt.getElseStmt();
		rep="if (" + condition.accept(this) + ")\n\t" + ifStmt.accept(this)+"\n";
		if (elseStmt != null) {
			rep += "else \n\t" + elseStmt.accept(this)+"\n";
		}
		return "";
	}

	@Override
	public String visit(BreakStmt stmt){
		return stmt.toString();
	}

	@Override
	public String visit(ContinueStmt stmt){
		return stmt.toString();
	}

	@Override
	public String visit(EmptyStmt stmt){
		return stmt.toString();
	}

	@Override
	public String visit(ForStmt stmt){
		Expression expr1 = stmt.getExpr1();
		Expression expr2 = stmt.getExpr2();
		Statement body = stmt.getBody();
		return "for "+stmt.getId()+"="+expr1.accept(this) +","+expr2.accept(this) +"\n\t"+body.accept(this);
	}

	@Override
	public String visit(WhileStmt stmt){
		Expression condition=stmt.getCondition();
		Statement body=stmt.getBody();
		return "while "+condition.accept(this)+"\n\t"+body.accept(this);
	}

	@Override
	public String visit(MethodCallStmt stmt){
		MethodCallExpr call=stmt.getCall();
		return call.accept(this)+";";
	}

	@Override
	public String visit(Block stmt){
		List<FieldDecl> declarations = stmt.getFields();
		List<Statement> statements = stmt.getStatements();

		String rep = "{\n";
		for (FieldDecl f: declarations) {
			rep += "\t"+f.accept(this)+"\n";
		}
	    for (Statement s: statements) {
			rep += "\t"+s.accept(this) + '\n';
		}
		rep += "}";
		
		return rep; 
	}


// visit expressions
	@Override
	public String visit(BinOpExpr expr) {
		BinOpType operator=expr.getOperator(); 
		Expression lOperand=expr.getLeftOperand(); 
		Expression rOperand=expr.getRightOperand();
		return lOperand.accept(this) + " " + operator + " " + rOperand.accept(this);
	}

	@Override
	public String visit(BinAndExpr expr){
		BinOpType operator=expr.getOperator(); 
		Expression lOperand=expr.getLeftOperand(); 
		Expression rOperand=expr.getRightOperand();
		return lOperand.accept(this) + " " + operator + " " + rOperand.accept(this);
	}

	@Override
	public String visit(BinDivideExpr expr){
		BinOpType operator=expr.getOperator(); 
		Expression lOperand=expr.getLeftOperand(); 
		Expression rOperand=expr.getRightOperand();
		return lOperand.accept(this) + " " + operator + " " + rOperand.accept(this);
	}

	@Override
	public String visit(BinEqualExpr expr){
		BinOpType operator=expr.getOperator(); 
		Expression lOperand=expr.getLeftOperand(); 
		Expression rOperand=expr.getRightOperand();
		return lOperand.accept(this) + " " + operator + " " + rOperand.accept(this);
	}

	@Override
	public String visit(BinGOETExpr expr){
		BinOpType operator=expr.getOperator(); 
		Expression lOperand=expr.getLeftOperand(); 
		Expression rOperand=expr.getRightOperand();
		return lOperand.accept(this) + " " + operator + " " + rOperand.accept(this);
	}

	@Override
	public String visit(BinGTExpr expr){
		BinOpType operator=expr.getOperator(); 
		Expression lOperand=expr.getLeftOperand(); 
		Expression rOperand=expr.getRightOperand();
		return lOperand.accept(this) + " " + operator + " " + rOperand.accept(this);
	}

	@Override
	public String visit(BinLOETExpr expr){
		BinOpType operator=expr.getOperator(); 
		Expression lOperand=expr.getLeftOperand(); 
		Expression rOperand=expr.getRightOperand();
		return lOperand.accept(this) + " " + operator + " " + rOperand.accept(this);
	}

	@Override
	public String visit(BinLTExpr expr){
		BinOpType operator=expr.getOperator(); 
		Expression lOperand=expr.getLeftOperand(); 
		Expression rOperand=expr.getRightOperand();
		return lOperand.accept(this) + " " + operator + " " + rOperand.accept(this);
	}

	@Override
	public String visit(BinMinusExpr expr){
		BinOpType operator=expr.getOperator(); 
		Expression lOperand=expr.getLeftOperand(); 
		Expression rOperand=expr.getRightOperand();
		return lOperand.accept(this) + " " + operator + " " + rOperand.accept(this);
	}

	@Override
	public String visit(BinModExpr expr){
		BinOpType operator=expr.getOperator(); 
		Expression lOperand=expr.getLeftOperand(); 
		Expression rOperand=expr.getRightOperand();
		return lOperand.accept(this) + " " + operator + " " + rOperand.accept(this);
	}

	@Override
	public String visit(BinNotEqualExpr expr){
		BinOpType operator=expr.getOperator(); 
		Expression lOperand=expr.getLeftOperand(); 
		Expression rOperand=expr.getRightOperand();
		return lOperand.accept(this) + " " + operator + " " + rOperand.accept(this);
	}

	@Override
	public String visit(BinOrExpr expr){
		BinOpType operator=expr.getOperator(); 
		Expression lOperand=expr.getLeftOperand(); 
		Expression rOperand=expr.getRightOperand();
		return lOperand.accept(this) + " " + operator + " " + rOperand.accept(this);
	}

	@Override
	public String visit(BinPlusExpr expr){
		BinOpType operator=expr.getOperator(); 
		Expression lOperand=expr.getLeftOperand(); 
		Expression rOperand=expr.getRightOperand();
		return lOperand.accept(this) + " " + operator + " " + rOperand.accept(this);
	}

	@Override
	public String visit(BinTimesExpr expr){
		BinOpType operator=expr.getOperator(); 
		Expression lOperand=expr.getLeftOperand(); 
		Expression rOperand=expr.getRightOperand();
		return lOperand.accept(this) + " " + operator + " " + rOperand.accept(this);
	}

	@Override
	public String visit(BracketExpr expr){
		Expression e=expr.getExpression();
		return "("+e.accept(this)+")";
	}

	@Override
	public String visit(MethodCallExpr expr){
		String rep;
		VarLocation method=expr.getMethod();
		List<Expression> params=expr.getParams();
		rep=method.accept(this)+"(";
		for ( Expression p : params ) {
			rep+=p.accept(this)+",";
		}
		rep+=")";
		return rep;
	}

	@Override
	public String visit(MinusExpr expr){
		Expression e=expr.getExpression();
		return "-"+e.accept(this);
	}

	@Override
	public String visit(NegatedExpr expr){
		Expression e=expr.getExpression();
		return "!"+e.accept(this);
	}

	
// visit literals	
	@Override
	public String visit(IntLiteral lit){
		return "";
	}

	@Override
	public String visit(FloatLiteral lit){
		return "";
	}

	@Override
	public String visit(BoolLiteral lit){
		return "";
	}


// visit locations	
	@Override
	public String visit(VarLocation loc){
		return "";
	}

	@Override
	public String visit(SubClassVarLocation loc){
		return "";
	}

	@Override
	public String visit(ArrayLocation loc){
		return "";
	}

	@Override
	public String visit(SubClassArrayLocation loc){
		return "";
	}



	
}