package ir.semcheck;

import java.util.LinkedList;
import java.util.List;

import ir.ASTVisitor;
import ir.ast.*;
import error.Error; // define class error


// type checker, concrete visitor 
public class TypeCheckVisitor implements ASTVisitor<String> {
	
	private int loops;
	private boolean isLoopContext(){ return loops > 0; }
	private void enterLoop() { loops +=1; }
	private void exitLoop(){ loops -=1;	}

	private boolean isMethodStatement;
	private void enterMStmt(){ isMethodStatement=true; }
	private void exitMStmt(){ isMethodStatement=false; }

	private String context,oldCont;
	private void setContext(String c){
		oldCont = new String(context);
		context = c;
	}
	private void releaseContext(){
		context = oldCont;
	}

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

	public TypeCheckVisitor(){
		errors = new LinkedList<Error>();
		context = Type.UNDEFINED;
	}


	@Override
	public String visit(Program dec){
		List<ClassDecl> classes=dec.getClasses();
		for ( ClassDecl c : classes ) {
			c.accept(this);
		}
		return "Program";
	}

	@Override
	public String visit(ClassDecl dec){
		List<FieldDecl> fields= dec.getFields();
		List<MethodDecl> methods = dec.getMethods();
		for ( FieldDecl f : fields ) {
			f.accept(this);
		}
		for ( MethodDecl m : methods ) {
			m.accept(this);
		}
		return "ClassDecl";
	}

	@Override
	public String visit(FieldDecl dec){
		List<VarDecl> elements = dec.getElements();
		for ( VarDecl v : elements ) {
			v.accept(this);
		}
		return "FieldDecl";
	}

	@Override
	public String visit(MethodDecl dec){
		String t = dec.getType();
		if (Type.isBasic(t) || t.equals("VOID")) {
		//Set Context
			setContext(t);
		//Check Params
			List<FormalParam> args = dec.getArgs();
			boolean isB,checkParam=true;
			for ( FormalParam p : args ) {
				isB=Type.isBasic(p.accept(this));
				if (!isB) {
					checkParam=false;
					addError(p,"Method params must be of a basic type");
				}
			}
		//Check Body
			if(checkParam){
				Body body = dec.getBody();
				String res = body.accept(this);
				/*if (!t.equals(res)) {
					if (res == "ExternalBody") {
						
					}
					else {
						addError(dec,"Incorrect type of return, expected '"+t+"', obtained '"+res+"'");
					}
				}*/
			}
			releaseContext();
		}
		else{
			addError(dec,"'"+t+"' is not an allowed type for a method");
		}
		return "MethodDecl";
	}

	@Override
	public String visit(VarDecl dec){
		return dec.getType();
	}

	@Override
	public String visit(ArrayDecl dec){
		return dec.getType();
	}

	@Override
	public String visit(FormalParam dec){
		return dec.getType();
	}

	@Override
	public String visit(Body dec){
		String res="ExternalBody";
		if (!dec.isExtern()) {
			Block b = dec.getBlock();
			res=b.accept(this);
		}
		return res;
	}


// visit statements
	@Override
	public String visit(AssignStmt stmt) {
		Location location=stmt.getLocation();
		Expression expr=stmt.getExpression();
		AssignOpType operator=stmt.getOperator();
		String lt = location.accept(this);
		if (!Type.isBasic(lt)) {
			addError(stmt,"Assignments must be done over basic types, found: '"+lt+"'");
		}
		else{
			String et = expr.accept(this);
			switch (operator) {
				case INCREMENT: case DECREMENT:
					if (!(Type.isNumerical(lt)&&Type.isNumerical(et))) {
						addError(stmt,"Increment and Decrement must be used between numerical expressions");
						break; //break only if error was found
					}
				default: //In any case check compatibility
					if (!Type.areCompatible(lt,et)) {
						addError(stmt,"Incompatible types, '"+et+"' can't be assigned to '"+lt+"'");
					}
					break;
			}

		}
		return "VOID";
	}

	@Override
	public String visit(ReturnStmt stmt){
		Expression expression = stmt.getExpression();
		if (expression==null) {
			if (!context.equals("VOID")) {
				addError(stmt,"Bad return type ('VOID'), in  context: '"+context+"'");
			}
		}
		else {
			String res = expression.accept(this);
			if (!context.equals(res)) {
				addError(stmt,"Bad return type ('"+res+"'), in  context: '"+context+"'");
			}
			return res;
		}
		return "VOID";
	}

	@Override
	public String visit(IfStmt stmt){
		String res = "VOID";
		Expression condition = stmt.getCondition();
		Statement ifStmt = stmt.getIfStmt();
		Statement elseStmt = stmt.getElseStmt();
	//Check condition type
		String condT = condition.accept(this);
		if (!Type.areCompatible(condT,"BOOL")) {
			addError(stmt,"If conditon must be a boolean expression");
		}
		//Check statements
		else {
			res=ifStmt.accept(this);
			if (elseStmt != null) elseStmt.accept(this);
		}
		return res;
	}

	@Override
	public String visit(BreakStmt stmt){
		if (!isLoopContext()) {
			addError(stmt,"Break statement outside loop");
		}
		return "VOID";
	}

	@Override
	public String visit(ContinueStmt stmt){
		if (!isLoopContext()) {
			addError(stmt,"Continue statement outside loop");
		}
		return "VOID";
	}

	@Override
	public String visit(EmptyStmt stmt){
		return "VOID";
	}

	@Override
	public String visit(ForStmt stmt){
		String res="VOID";
		Expression expr1 = stmt.getExpr1();
		String te1 = expr1.accept(this);
		if (!Type.areCompatible("INT",te1)) {
			addError(stmt,"For's initialization must be integer type");
		}
		else{
			Expression expr2 = stmt.getExpr2();
			String te2 = expr2.accept(this);
			if (!Type.areCompatible("INT",te2)) {
				addError(stmt,"For's limit expression must be integer type");
			}
			else{
				Statement body = stmt.getBody();
				res = body.accept(this);
			}
		}
		return res;
	}

	@Override
	public String visit(WhileStmt stmt){
		String res ="VOID";
		enterLoop();
		Expression condition=stmt.getCondition();
		String condT = condition.accept(this);
		if (!Type.areCompatible(condT,"BOOL")) {
			addError(stmt,"While conditon must be a boolean expression");
		}
		else {
			Statement body=stmt.getBody();
			body.accept(this);
		}
		exitLoop();
		return res;
	}

	@Override
	public String visit(MethodCallStmt stmt){
		enterMStmt();
		MethodCallExpr call=stmt.getCall();
		String et = call.accept(this);
		exitMStmt();
		return et;
	}

	@Override
	public String visit(Block stmt){
		List<FieldDecl> declarations = stmt.getFields();
		List<Statement> statements = stmt.getStatements();
		for (FieldDecl f: declarations) {
			f.accept(this);
		}
		for (Statement s: statements) {
			s.accept(this);
		}
		return "VOID";
	}


// visit expressions
	@Override
	public String visit(BinOpExpr expr) {
		return Type.UNDEFINED;
	}

	//arithmetical expressions
	private String arithORrelVisit(BinOpExpr expr, String et){
		Expression lOperand=expr.getLeftOperand(); 
		Expression rOperand=expr.getRightOperand();
		String tl = lOperand.accept(this);
		if (!Type.isNumerical(tl)) {
			addError(lOperand,et+" expressions must be used with numerical types");
		}
		else {
			String tr = rOperand.accept(this);
			if (!Type.isNumerical(tr)) {
				addError(rOperand,et+" expressions must be used with numerical types");
			}
			else {
				if (!Type.areCompatible(tl,tr)) {
					addError(expr,"Incompatible types in "+et+" expression: '"+tl+"' & '"+tr+"'");
				}
				else {
					if (et.equals("arithmetical")) {
						return Type.toSingle(tl);
					}
					else return "BOOL";
				}
			}
		}
		return "VOID";
	}

	@Override
	public String visit(BinPlusExpr expr){
		return arithORrelVisit(expr,"arithmetical");
	}

	@Override
	public String visit(BinTimesExpr expr){
		return arithORrelVisit(expr,"arithmetical");
	}

	@Override
	public String visit(BinDivideExpr expr){
		return arithORrelVisit(expr,"arithmetical");
	}

	@Override
	public String visit(BinMinusExpr expr){
		return arithORrelVisit(expr,"arithmetical");
	}

	@Override
	public String visit(BinModExpr expr){
		return arithORrelVisit(expr,"arithmetical");
	}
	

	//Relational Expressions

	@Override
	public String visit(BinGOETExpr expr){
		return arithORrelVisit(expr,"relational");
	}

	@Override
	public String visit(BinGTExpr expr){
		return arithORrelVisit(expr,"relational");
	}

	@Override
	public String visit(BinLOETExpr expr){
		return arithORrelVisit(expr,"relational");
	}

	@Override
	public String visit(BinLTExpr expr){
		return arithORrelVisit(expr,"relational");
	}


	//Comparison Expressions
	private String compExpr(BinOpExpr expr){
		Expression lOperand=expr.getLeftOperand(); 
		Expression rOperand=expr.getRightOperand();
		String tl = lOperand.accept(this);
		String tr = rOperand.accept(this);
		if (!Type.areCompatible(tl,tr)) {
			addError(expr,"Incompatible types in comparison expression: '"+tl+"' & '"+tr+"'");
		}
		else {
			return "BOOL";
		}
		return "VOID";
	}

	@Override
	public String visit(BinEqualExpr expr){
		return compExpr(expr);
	}

	@Override
	public String visit(BinNotEqualExpr expr){
		return compExpr(expr);
	}
	

	//Conditional Expressions
	private String condExpr(BinOpExpr expr){
		Expression lOperand=expr.getLeftOperand(); 
		Expression rOperand=expr.getRightOperand();
		String tl = lOperand.accept(this);
		if (!Type.isBoolean(tl)) {
			addError(lOperand,"Conditional expressions must be used with boolean types");
		}
		else {
			String tr = rOperand.accept(this);
			if (!Type.isBoolean(tr)) {
				addError(rOperand,"Conditional expressions must be used with boolean types");
			}
			else {
				return "BOOL";
			}
		}
		return "VOID";
	}
	
	@Override
	public String visit(BinAndExpr expr){
		return condExpr(expr);

	}

	@Override
	public String visit(BinOrExpr expr){
		return condExpr(expr);
	}

	@Override
	public String visit(NegatedExpr expr){
		Expression e=expr.getExpression();
		String et = e.accept(this);
		if (!Type.isBoolean(et)) {
			addError(expr,"Negated expression must be used with boolean expression");
		}
		else{
			return "BOOL";
		}
		return "VOID";
	}
		
	@Override
	public String visit(BracketExpr expr){
		Expression e=expr.getExpression();
		return e.accept(this);
	}

	@Override
	public String visit(MinusExpr expr){
		Expression e=expr.getExpression();
		String et = e.accept(this);
		if (!Type.isNumerical(et)) {
			addError(expr,"Minus expression must be used with numerical expression");
		}
		else{
			return Type.toSingle(et);
		}
		return "VOID";
	}

	@Override
	public String visit(MethodCallExpr expr){
		VarLocation method=expr.getMethod();
		List<Expression> actArgs=expr.getParams();
		List<FormalParam> formalParams = ((MethodDecl)method.getRef()).getArgs();
		//Check arguments type
		String argT,fpT;
		boolean ok=true;
		for (int i=0 ; i<formalParams.size() ; i++ ) {
			argT = actArgs.get(i).accept(this);
			fpT = formalParams.get(i).getType();
			if (!Type.areCompatible(argT,fpT)) {
				ok=false;
				addError(actArgs.get(i),"Incompatible types in method call arguments, expected: '"+fpT+"', found: '"+argT+"'");
			}
		}
		if (ok) {
			String mt =method.getType();
			//Methods used as expressions must return a concrete type
			if (!isMethodStatement && mt.equals("VOID")) { 
				addError(expr,"void methods can be only used as statements");
			}
			else {
				return mt;
			}
		}
		return "VOID";
	}
	

	
// visit literals	
	@Override
	public String visit(IntLiteral lit){
		return "INT";
	}

	@Override
	public String visit(FloatLiteral lit){
		return "FLOAT";
	}

	@Override
	public String visit(BoolLiteral lit){
		return "BOOL";
	}


// visit locations	
	@Override
	public String visit(VarLocation loc){
		return loc.getType();
	}

	@Override
	public String visit(SubClassVarLocation loc){
		return loc.getType();
	}

	@Override
	public String visit(ArrayLocation loc){
		Expression index = loc.getIndex();
		String it = index.accept(this);
		if (!it.equals("INT")) {
			addError(loc,"Array's index must be of type 'INT', found: '"+it+"'");
			return "VOID";
		}
		return loc.getType();
	}

	@Override
	public String visit(SubClassArrayLocation loc){
		Expression index = loc.getIndex();
		String it = index.accept(this);

		if (!it.equals("INT")) {
			addError(loc,"Array's index must be of type 'INT', found: '"+it+"'");
			return "VOID";
		}
		return loc.getType();
	}



	
}
