package ir.semcheck;

import java.util.LinkedList;
import java.util.List;

import ir.tac.*;
import ir.ASTVisitor;
import ir.ast.*;
import error.Error; // define class error

public class TACVisitor implements ASTVisitor<VarDecl> {

	private List<TAC> instls; //Three-Adress-Code instructions list
	private boolean addInst(Inst inst, Object op1, AST op2, VarDecl res){
		return instls.add(new TAC(inst,op1,op2,res));
	}
	public List<TAC> getList(){
		return instls;
	}

	/***********************************************************************
	*	temporaly variables management
	*/
	private int temps;		//temporal vars counter
	private VarDecl newTemp(){
		temps++;
		return new VarDecl("t"+temps);
	}

	/***********************************************************************/

	/***********************************************************************
	*	identifiers management
	*/
	private int ifcount;		
	private int newIf(){
		ifcount++;
		return ifcount;
	}

	private int whilecount;		
	private int newWhile(){
		whilecount++;
		return whilecount;
	}

	private int forcount;		
	private int newFor(){
		forcount++;
		return forcount;
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
		addInst(Inst.PROGRAMINIT,null,null,null);
		List<ClassDecl> classes=dec.getClasses();
		for ( ClassDecl c : classes ) {
			c.accept(this);
		}
		addInst(Inst.PROGRAMEND,null,null,null);
		return null;
	}

	@Override
	public VarDecl visit(ClassDecl dec){
		addInst(Inst.CLASSINIT,dec.getId(),null,null);
		List<FieldDecl> fields= dec.getFields();
		for ( FieldDecl f : fields ) {
			f.accept(this);
		}
		List<MethodDecl> methods = dec.getMethods();
		for ( MethodDecl m : methods ) {
			m.accept(this);
		}
		addInst(Inst.CLASSEND,null,null,null);
		return new VarDecl("null");
	}
	
	@Override
	public VarDecl visit(FieldDecl dec){
		List<VarDecl> elements = dec.getElements();
		for ( VarDecl v : elements ) {
 			v.accept(this);
 		}
		return new VarDecl("null");
	}
	
	@Override
	public VarDecl visit(MethodDecl dec){
		addInst(Inst.METHODINIT,dec.getId(),null,null);
		List<FormalParam> args = dec.getArgs();
		for ( FormalParam a : args ) {
			a.accept(this);
		} 
		dec.getBody().accept(this);
		addInst(Inst.METHODEND,null,null,null);
		return new VarDecl("null");
	}
	
	@Override
	public VarDecl visit(VarDecl dec){
		switch (dec.getType()) {
			case "INT": addInst(Inst.DECVARINT,dec,null,null); break;
			case "FLOAT": addInst(Inst.DECVARFLT,dec,null,null); break;
			case "BOOL": addInst(Inst.DECVARBOOL,dec,null,null); break;
		}

		return new VarDecl("null");
	}
		
	@Override
	public VarDecl visit(ArrayDecl dec){
		switch (dec.getType()) {
			case "INTARRAY": addInst(Inst.DECVARINTARRAY,dec,null,null); break;
			case "FLOATARRAY": addInst(Inst.DECVARFLTARRAY,dec,null,null); break;
			case "BOOLARRAY": addInst(Inst.DECVARBOOLARRAY,dec,null,null); break;
		}
		return new VarDecl("null");
	}
		
	@Override
	public VarDecl visit(FormalParam dec){
		switch (dec.getType()) {
			case "INT": addInst(Inst.DECVARINT,dec,null,null); break;
			case "FLOAT": addInst(Inst.DECVARFLT,dec,null,null); break;
			case "BOOL": addInst(Inst.DECVARBOOL,dec,null,null); break;
			case "INTARRAY": addInst(Inst.DECVARINTARRAY,dec,null,null); break;
			case "FLOATARRAY": addInst(Inst.DECVARFLTARRAY,dec,null,null); break;
			case "BOOLARRAY": addInst(Inst.DECVARBOOLARRAY,dec,null,null); break;
		}
		return new VarDecl("null");
	}
	
	@Override
	public VarDecl visit(Body dec){
		if (!dec.isExtern()) {
			dec.getBlock().accept(this);
		}
		else{
			addInst(Inst.EXTERNBODY,dec,null,null);
		}
		return new VarDecl("null");
	}
	

// visit statements
	@Override
	public VarDecl visit(AssignStmt stmt){
		addInst(Inst.ASSIGN,stmt.getExpression().accept(this),null,stmt.getLocation().accept(this));
		return new VarDecl("null");
	}
	
	@Override
	public VarDecl visit(ReturnStmt stmt){
		Expression expr = stmt.getExpression();
		if (expr!=null) {
			addInst(Inst.RETURN,null,null,expr.accept(this));
		}
		else {
			addInst(Inst.NULLRETURN,null,null,null);
		}
		return new VarDecl("null");
	}
	
	@Override
	public VarDecl visit(IfStmt stmt){
		Expression condition = stmt.getCondition();
		Statement ifStmt = stmt.getIfStmt();
		Statement elseStmt = stmt.getElseStmt();
		VarDecl condRes = condition.accept(this);
		int ifid=newIf();
		addInst(Inst.JF,"EndIf"+ifid,condRes,null);
		ifStmt.accept(this);
		addInst(Inst.LABEL,"EndIf"+ifid,null,null);
		if (elseStmt!=null) {
			elseStmt.accept(this);
		}

		return new VarDecl("null");
	}
	
	@Override
	public VarDecl visit(BreakStmt stmt){
		addInst(Inst.BREAK,null,null,null);
		return new VarDecl("null");
	}
	
	@Override
	public VarDecl visit(ContinueStmt stmt){
		addInst(Inst.CONTINUE,null,null,null);
		return new VarDecl("null");
	}
	
	@Override
	public VarDecl visit(EmptyStmt stmt){
		return new VarDecl("null");
	}
	
	@Override
	public VarDecl visit(ForStmt stmt){
		VarDecl inicRes = stmt.getExpr1().accept(this);
		VarDecl i = stmt.getId();
		addInst(Inst.ASSIGN,inicRes,null,i);
		VarDecl limit = stmt.getExpr2().accept(this);
		int forid = newFor();
		addInst(Inst.LABEL,"InitFor"+forid,null,null);
		VarDecl compRes = newTemp();
		addInst(Inst.CMP,i,limit,compRes); // i-limit
		addInst(Inst.JGE,"EndFor"+forid,compRes,null); //If i>=limit
		stmt.getBody().accept(this);
		addInst(Inst.ADDCONST,1,null,i);	//i++
		addInst(Inst.JMP,"InitFor"+forid,null,null);
		addInst(Inst.LABEL,"EndFor"+forid,null,null);
		return new VarDecl("null");
	}
	
	@Override
	public VarDecl visit(WhileStmt stmt){
		int whileid = newWhile();
		addInst(Inst.LABEL,"InitWhile"+whileid,null,null);
		VarDecl condRes = stmt.getCondition().accept(this);
		addInst(Inst.JF,"EndWhile"+whileid,condRes,null);
		stmt.getBody().accept(this);
		addInst(Inst.JMP,"InitWhile"+whileid,null,null);
		addInst(Inst.LABEL,"EndWhile"+whileid,null,null);
		return new VarDecl("null");
	}
	
	@Override
	public VarDecl visit(MethodCallStmt stmt){
		addInst(Inst.CALLSTMT,stmt.getCall().getMethod().getId(),null,null);
		return new VarDecl("null");
	}
	
	@Override
	public VarDecl visit(Block stmt){
		List<FieldDecl> declarations = stmt.getFields();
		for (FieldDecl f: declarations) {
			f.accept(this);
		}

		List<Statement> statements = stmt.getStatements();
	    for (Statement s: statements) {
			s.accept(this);
		}
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
			case "FLOAT": addInst(Inst.PLUSFLT,op1,op2,res); break;
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
			case "FLOAT": addInst(Inst.MINUSFLT,op1,op2,res); break;
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
			case "FLOAT": addInst(Inst.TIMESFLT,op1,op2,res); break;
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
			case "FLOAT": addInst(Inst.DIVIDEFLT,op1,op2,res); break;
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
			case "FLOAT": addInst(Inst.MODFLT,op1,op2,res); break;
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
			case "FLOAT": addInst(Inst.GTFLT,op1,op2,res); break;
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
			case "FLOAT": addInst(Inst.GOETFLT,op1,op2,res); break;
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
			case "FLOAT": addInst(Inst.LTFLT,op1,op2,res); break;
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
			case "FLOAT": addInst(Inst.LOETFLT,op1,op2,res); break;
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
			case "FLOAT": addInst(Inst.EQFLT,op1,op2,res); break;
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
			case "FLOAT": addInst(Inst.NEQFLT,op1,op2,res); break;
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
		System.out.println("1");
		VarDecl res = newTemp();
		System.out.println("2");
		switch (op1.getType()) {
			case "INT": addInst(Inst.UMINUSINT,op1,null,res); break;
			case "FLOAT": addInst(Inst.UMINUSFLT,op1,null,res); break;
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
		VarDecl res = newTemp();
		addInst(Inst.CALLEXPR,expr.getMethod().getId(),null,res);
		return res;
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
	private VarDecl locationVisit(Location loc){
		VarDecl res = newTemp();
		switch (loc.getType()) {
			case "INT": addInst(Inst.LMINT,loc,null,res); break; 
			case "FLOAT": addInst(Inst.LMFLT,loc,null,res); break;
			case "BOOL": addInst(Inst.LMBOOL,loc,null,res); break;
		}
		return res;
	}

	@Override
	public VarDecl visit(VarLocation loc){
		return locationVisit(loc);
	}
	
	@Override
	public VarDecl visit(SubClassVarLocation loc){
		return locationVisit(loc);
	}
	
	@Override
	public VarDecl visit(ArrayLocation loc){
		return locationVisit(loc);
	}
	
	@Override
	public VarDecl visit(SubClassArrayLocation loc){
		return locationVisit(loc);
	}
	
	
}