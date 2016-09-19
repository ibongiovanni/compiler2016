package ir.semcheck;

import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;

import ir.ASTVisitor;
import ir.ast.*;
import ir.symboltable.*;
import error.Error; // define class error


// type checker, concrete visitor 
public class BuilderVisitor implements ASTVisitor<Boolean> {
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

	private SymbolTable stack;

	public BuilderVisitor(){
		errors = new LinkedList<Error>();
		stack = new SymbolTable();
	}

	@Override
	public Boolean visit(Program dec){
		List<ClassDecl> classes=dec.getClasses();
		Boolean res=true;
		stack.newLevel(dec);
		Iterator<ClassDecl> it = classes.iterator();
		ClassDecl c;
		while (it.hasNext() && res) {
			c = it.next();
			if ( stack.addClass( c.getId() ) ) {
				res = res&&c.accept(this);	
			}
			else {
				res = false;
				//repeated class name
			}
		}
		return res;
	}

	@Override
	public Boolean visit(ClassDecl dec){
		Boolean res=true;
		List<FieldDecl> fields= dec.getFields();
		List<MethodDecl> methods = dec.getMethods();
		stack.newLevel(dec);
		Iterator<FieldDecl> fit = fields.iterator();
		FieldDecl f;
		while (fit.hasNext()&&res) {
			f = fit.next();
			res=res&&f.accept(this);
		}
		Iterator<MethodDecl> mit = methods.iterator();
		MethodDecl m;
		while (mit.hasNext()&&res) {
			m = mit.next();
			res=res&&m.accept(this);
		}
		stack.closeLevel();
		return res;
	}


	@Override
	public Boolean visit(FieldDecl dec){
		Boolean res=true;
		List<VarDecl> elements = dec.getElements();
		Iterator<VarDecl> eit = elements.iterator();
		VarDecl v;
		while (eit.hasNext()&&res) {
			v = eit.next();
			res=res&&v.accept(this);
		}
		return res;
	}

	@Override
	public Boolean visit(MethodDecl dec){
		Boolean res=true;
		if ( stack.addMethod(dec) ) {
			stack.newLevel(dec); //New MethodArgsLevel
			List<FormalParam> args = dec.getArgs();
			Iterator<FormalParam> ait = args.iterator();
			FormalParam a;
			while (ait.hasNext()&&res) {
				a = ait.next();
				res=res&&a.accept(this);
			}
			if (res) {
				Body b = dec.getBody();
				res=res&&b.accept(this);	
			}
			stack.closeLevel();
		}
		else { res=false; }
		return res;
	}

	@Override
	public Boolean visit(VarDecl dec){
		Boolean res=true;
		res=stack.addVar(dec);
		return res;
	}

	@Override
	public Boolean visit(ArrayDecl dec){
		Boolean res=true;
		res=stack.addVar(dec);
		return res;
	}

	@Override
	public Boolean visit(FormalParam dec){
		Boolean res=true;
		res=stack.addArg(dec);
		return res;
	}

	@Override
	public Boolean visit(Body dec){
		Boolean res=true;
		if (!dec.isExtern()) {
			Block b = dec.getBlock();
			res=b.accept(this);
		}
		return res;
	}


// visit statements
	@Override
	public Boolean visit(Block stmt){
		Boolean res=true;
		stack.newLevel(stmt);
			List<FieldDecl> declarations=stmt.getFields();
			Iterator<FieldDecl> fit = declarations.iterator();
			FieldDecl f;
			while (fit.hasNext()&&res) {
				f = fit.next();
				res &= f.accept(this);
			}

			List<Statement> statements=stmt.getStatements();
			Iterator<Statement> sit = statements.iterator();
			Statement s;
			while (sit.hasNext()&&res) {
				s = sit.next();
				res &= s.accept(this);
			}
		stack.closeLevel();
		return res;
	}


	@Override
	public Boolean visit(AssignStmt stmt) {
		Boolean res=true;
		Location location=stmt.getLocation();
		Expression expr=stmt.getExpression();
		res=location.accept(this);
		res=res&&expr.accept(this);
		return res;
	}

	@Override
	public Boolean visit(ReturnStmt stmt){
		Boolean res=true;
		Expression expr = stmt.getExpression();
		if (expr!=null) {
			res=expr.accept(this);
		}
		return res;
	}

	@Override
	public Boolean visit(IfStmt stmt){
		Boolean res=true;
		Expression condition = stmt.getCondition();
		Statement ifStmt = stmt.getIfStmt();
		Statement elseStmt = stmt.getElseStmt();
		res=condition.accept(this);
		res=res&&ifStmt.accept(this);
		if (elseStmt!=null) {
			res=res&&elseStmt.accept(this);
		}
		return res;
	}

	@Override
	public Boolean visit(BreakStmt stmt){
		Boolean res=true;
		
		return res;
	}

	@Override
	public Boolean visit(ContinueStmt stmt){
		Boolean res=true;
		
		return res;
	}

	@Override
	public Boolean visit(EmptyStmt stmt){
		Boolean res=true;
		
		return res;
	}

	@Override
	public Boolean visit(ForStmt stmt){
		Boolean res=true;
		Expression expr1 = stmt.getExpr1();
		Expression expr2 = stmt.getExpr2();
		Statement body = stmt.getBody();
		res &= expr1.accept(this);
		res &= expr2.accept(this);
		res &= body.accept(this);
		return res;
	}

	@Override
	public Boolean visit(WhileStmt stmt){
		Boolean res=true;
		Expression condition=stmt.getCondition();
		Statement body=stmt.getBody();
		res &= condition.accept(this);
		res &= body.accept(this);
		return res;
	}

	@Override
	public Boolean visit(MethodCallStmt stmt){
		Boolean res=true;
		MethodCallExpr call=stmt.getCall();
		res &= call.accept(this);
		return res;
	}

	


// visit expressions
	@Override
	public Boolean visit(BinOpExpr expr) {
		Boolean res=true;
		Expression lOperand=expr.getLeftOperand(); 
		res &= lOperand.accept(this);
		Expression rOperand=expr.getRightOperand();
		res &= rOperand.accept(this);
		return res;
	}

	@Override
	public Boolean visit(BinAndExpr expr){
		Boolean res=true;
		Expression lOperand=expr.getLeftOperand(); 
		res &= lOperand.accept(this);
		Expression rOperand=expr.getRightOperand();
		res &= rOperand.accept(this);
		return res;
	}

	@Override
	public Boolean visit(BinDivideExpr expr){
		Boolean res=true;
		Expression lOperand=expr.getLeftOperand(); 
		res &= lOperand.accept(this);
		Expression rOperand=expr.getRightOperand();
		res &= rOperand.accept(this);
		return res;
	}

	@Override
	public Boolean visit(BinEqualExpr expr){
		Boolean res=true;
		Expression lOperand=expr.getLeftOperand(); 
		res &= lOperand.accept(this);
		Expression rOperand=expr.getRightOperand();
		res &= rOperand.accept(this);
		return res;
	}

	@Override
	public Boolean visit(BinGOETExpr expr){
		Boolean res=true;
		Expression lOperand=expr.getLeftOperand(); 
		res &= lOperand.accept(this);
		Expression rOperand=expr.getRightOperand();
		res &= rOperand.accept(this);
		return res;
	}

	@Override
	public Boolean visit(BinGTExpr expr){
		Boolean res=true;
		Expression lOperand=expr.getLeftOperand(); 
		res &= lOperand.accept(this);
		Expression rOperand=expr.getRightOperand();
		res &= rOperand.accept(this);
		return res;
	}

	@Override
	public Boolean visit(BinLOETExpr expr){
		Boolean res=true;
		Expression lOperand=expr.getLeftOperand(); 
		res &= lOperand.accept(this);
		Expression rOperand=expr.getRightOperand();
		res &= rOperand.accept(this);
		return res;
	}

	@Override
	public Boolean visit(BinLTExpr expr){
		Boolean res=true;
		Expression lOperand=expr.getLeftOperand(); 
		res &= lOperand.accept(this);
		Expression rOperand=expr.getRightOperand();
		res &= rOperand.accept(this);
		return res;
	}

	@Override
	public Boolean visit(BinMinusExpr expr){
		Boolean res=true;
		Expression lOperand=expr.getLeftOperand(); 
		res &= lOperand.accept(this);
		Expression rOperand=expr.getRightOperand();
		res &= rOperand.accept(this);
		return res;
	}

	@Override
	public Boolean visit(BinModExpr expr){
		Boolean res=true;
		Expression lOperand=expr.getLeftOperand(); 
		res &= lOperand.accept(this);
		Expression rOperand=expr.getRightOperand();
		res &= rOperand.accept(this);
		return res;
	}

	@Override
	public Boolean visit(BinNotEqualExpr expr){
		Boolean res=true;
		Expression lOperand=expr.getLeftOperand(); 
		res &= lOperand.accept(this);
		Expression rOperand=expr.getRightOperand();
		res &= rOperand.accept(this);
		return res;
	}

	@Override
	public Boolean visit(BinOrExpr expr){
		Boolean res=true;
		Expression lOperand=expr.getLeftOperand(); 
		res &= lOperand.accept(this);
		Expression rOperand=expr.getRightOperand();
		res &= rOperand.accept(this);
		return res;
	}

	@Override
	public Boolean visit(BinPlusExpr expr){
		Boolean res=true;
		Expression lOperand=expr.getLeftOperand(); 
		res &= lOperand.accept(this);
		Expression rOperand=expr.getRightOperand();
		res &= rOperand.accept(this);
		return res;
	}

	@Override
	public Boolean visit(BinTimesExpr expr){
		Boolean res=true;
		Expression lOperand=expr.getLeftOperand(); 
		res &= lOperand.accept(this);
		Expression rOperand=expr.getRightOperand();
		res &= rOperand.accept(this);
		return res;
	}

	@Override
	public Boolean visit(BracketExpr expr){
		Boolean res=true;
		Expression e=expr.getExpression();
		res &= e.accept(this);
		return res;
	}


	@Override
	public Boolean visit(MinusExpr expr){
		Boolean res=true;
		Expression e=expr.getExpression();
		res &= e.accept(this);
		return res;
	}

	@Override
	public Boolean visit(NegatedExpr expr){
		Boolean res=true;
		Expression e=expr.getExpression();
		res &= e.accept(this);
		return res;
	}

	@Override
	public Boolean visit(MethodCallExpr expr){
		Boolean res=true;
		//search for method declaration
		VarLocation method=expr.getMethod();
		MethodDecl dec=stack.searchMethod(method.getId());
		if (dec!=null) {
			expr.setType(dec.getType());
			method.setType(dec.getType());
			method.setRef(dec);
		}
		else{
			//Not found method
			res=false;
		}
		//Check arguments
		List<Expression> params=expr.getParams();
		Iterator<Expression> pit = params.iterator();
		Expression p;
		while (pit.hasNext()&&res) {
			p=pit.next();
			res &= p.accept(this);
		}
		return res;
	}
	
// visit literals	
	@Override
	public Boolean visit(IntLiteral lit){
		Boolean res=true;
		
		return res;
	}

	@Override
	public Boolean visit(FloatLiteral lit){
		Boolean res=true;
		
		return res;
	}

	@Override
	public Boolean visit(BoolLiteral lit){
		Boolean res=true;
		
		return res;
	}


// visit locations	
	@Override
	public Boolean visit(VarLocation loc){
		Boolean res=true;
		AST decl = stack.searchId(loc.getId());
		if (decl!=null) {
			if (decl instanceof ArrayDecl) {
				//Incorrect type of location
				res=false;
			}
			else {
				if (decl instanceof FormalParam) { //The location refers a param
					FormalParam p = (FormalParam) decl;
					loc.setType(p.getType());
					loc.setRef(p);
				}
				else{
					VarDecl v = (VarDecl) decl;
					loc.setType(v.getType());
					loc.setRef(v);
				}
			}
		}
		else {
			//Not found location
			res=false;
		}
		return res;
	}

	@Override
	public Boolean visit(SubClassVarLocation loc){
		Boolean res=true;
		String cl="";
		List<String> classes = loc.getClasses();
		for ( String c : classes ) {
			cl += c+".";
		}
		VarLocation pub = stack.searchPublic(cl,loc.getId());
		if (pub!=null) {
			if (pub.getRef() instanceof VarDecl) {
				if (pub.getRef() instanceof ArrayDecl) {
					//incorrect type location
					res = false;
				}
				else {
					VarDecl v = (VarDecl) pub.getRef();
					loc.setType(v.getType());
					loc.setRef(v);
				}
			}
			else {
				if (pub.getRef() instanceof MethodDecl) {
					
				}
			}
		}
		else {
			res=false;
			//Not found location
		}
		return res;
	}

	@Override
	public Boolean visit(ArrayLocation loc){
		Boolean res=true;
		AST decl = stack.searchId(loc.getId());
		if (decl!=null) {
			if (!(decl instanceof ArrayDecl)) {
				//Incorrect type of location
				res=false;
			}
			else {
				ArrayDecl v = (ArrayDecl) decl;
				loc.setType(v.getType());
				loc.setRef(v);
			}
		}
		else {
			//Not found location
			res=false;
		}
		return res;
	}

	@Override
	public Boolean visit(SubClassArrayLocation loc){
		Boolean res=true;
		String cl="";
		List<String> classes = loc.getClasses();
		for ( String c : classes ) {
			cl += c+".";
		}
		VarLocation pub = stack.searchPublic(cl,loc.getId());
		if (pub!=null) {
			if (pub.getRef() instanceof ArrayDecl) {
				ArrayDecl v = (ArrayDecl) pub.getRef();
				loc.setType(v.getType());
				loc.setRef(v);
			}
			else {
				if (pub.getRef() instanceof VarDecl) {
					res = false;
					//incorrect type location
				}
			}
		}
		else {
			res=false;
			//Not found location
		}
		return res;
	}

}