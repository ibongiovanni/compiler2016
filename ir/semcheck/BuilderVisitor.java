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
				addError(c,"Repeated Class Name: '"+c.getId()+"'");
			}
		}
		// Search for main class
		if (res && !stack.searchClass("main")) {
			res=false;
			addError(dec,"There's no main class declared");
		}
		if (res) {
			VarLocation pub = stack.searchPublic("main","main");
			if (pub!=null) {
				if (!(pub.getRef() instanceof MethodDecl)) {
					res=false;
					addError(dec,"main class should have declared a main method, not an attribute");
				}
			}
			else {
				res=false;
				addError(dec,"main class should have declared a main method");
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
		if (stack.isCurrentLevelClassDeclarations() && !Type.isBasic(dec.getType())) {
			res=false;
			addError(dec,"Attributes should be of a basic type");
		}
		else{
			List<VarDecl> elements = dec.getElements();
			Iterator<VarDecl> eit = elements.iterator();
			VarDecl v;
			while (eit.hasNext()&&res) {
				v = eit.next();
				res=res&&v.accept(this);
			}
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
		else { 
			res=false;
			addError(dec,"Cannot add method '"+dec.getId()+"'. Identifier already used");
		}
		return res;
	}

	@Override
	public Boolean visit(VarDecl dec){
		Boolean res=true;
		String t = dec.getType();
		if (!Type.isBasic(t)) {
			res &= stack.searchClass(t);
		}
		if (res) {
				res=stack.addVar(dec);
				if(!res){
					addError(dec,"Cannot add var '"+dec.getId()+"'. Identifier already used");
				}
		}
		else {
			addError(dec, "Not declared class '"+t+"'");	
		}	
		return res;
	}

	@Override
	public Boolean visit(ArrayDecl dec){
		Boolean res=true;
		res=stack.addVar(dec);
		if (!res) {
			addError(dec,"Cannot add array '"+dec.getId()+"'. Identifier already used");
		}
		return res;
	}

	@Override
	public Boolean visit(FormalParam dec){
		Boolean res=true;
		res=stack.addArg(dec);
		if (!res) {
			addError(dec,"Cannot add param '"+dec.getId()+"'. Identifier already used");
		}
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
		if (method instanceof SubClassVarLocation) {
			String cl="";
			List<String> classes = ((SubClassVarLocation)method).getClasses();
			if (classes.size()>1) {
				res=false;
				addError(expr,"Navigation not allowed");
			}
			else {
				VarLocation varloc = new VarLocation(classes.get(0)); //Search for local declaration
				if (varloc.accept(this)) {
					// Now search for public attribute
					cl = varloc.getType(); //Get class name
					VarLocation pub = stack.searchPublic(cl,method.getId());
					if (pub!=null) {
						if (!(pub.getRef() instanceof MethodDecl)) {
							res=false;
							addError(expr,"Missused location. Identifier '"+method.getId()+"' is not declared as a method");
						}
						else {
							expr.setType(pub.getRef().getType());
							method.setType(pub.getRef().getType());
							method.setRef(pub.getRef());
						}
					}
					else {
						res=false;
						if (stack.searchClass(cl)) {
							addError(expr,"Cannot find method '"+method.getId()+"' in class '"+cl+"'");
						}
						else {
							addError(expr,"Cannot find class '"+cl+"'");
						}
					}
				}
			}
		}
		//Local method
		else {
			MethodDecl dec=stack.searchMethod(method.getId());
			if (dec!=null) {
				expr.setType(dec.getType());
				method.setType(dec.getType());
				method.setRef(dec);
			}
			else{
				//Not found method		
				res=false;
				addError(expr,"Cannot find method '"+method.getId()+"'");
			}
		}
		//Check arguments
		if(res){
			List<Expression> arguments=expr.getParams();
			//Check number of arguments is same as params declared
			MethodDecl m = (MethodDecl) method.getRef();
			res &= (m.getArgs().size())==arguments.size();
			if(res){	
				Iterator<Expression> ait = arguments.iterator();
				Expression a;
				while (ait.hasNext()&&res) {
					a=ait.next();
					res &= a.accept(this);
				}
			}
			else {
				addError(expr,"Actual and formal argument lists for method '"+method.getId()+"' differ in length, expected: "+m.getArgs().size()+", found: "+arguments.size());
			}
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
				addError(loc,"Incorrect type of var '"+loc.getId()+"'. Found Array, Expected Var");
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
			addError(loc,"Cannot find variable '"+loc.getId()+"'");
		}
		return res;
	}

	@Override
	public Boolean visit(SubClassVarLocation loc){
		Boolean res=true;
		String cl="";
		List<String> classes = loc.getClasses();
		if (classes.size()>1) {
			res=false;
			addError(loc,"Navigation not allowed");
		}
		else {
			VarLocation varloc = new VarLocation(classes.get(0)); //Search for local declaration
			if (varloc.accept(this)) {
				// Now search for public attribute
				cl = varloc.getType(); //Get class name
				VarLocation pub = stack.searchPublic(cl,loc.getId());
				if (pub!=null) {
					if (pub.getRef() instanceof VarDecl) {
						if (pub.getRef() instanceof ArrayDecl) {
							//incorrect type location
							res = false;					
							addError(loc,"Incorrect type of var '"+cl+loc.getId()+"'. Found Array, Expected Var");
						}
						else {
							VarDecl v = (VarDecl) pub.getRef();
							loc.setType(v.getType());
							loc.setRef(v);
						}
					}
					else {
						if (pub.getRef() instanceof MethodDecl) {
							res = false;
							addError(loc,"Missused location. Identifier '"+cl+"."+loc.getId()+"' is not declared as a attribute");
						}
					}
				}
				else {
					res=false;
					//Not found location
					addError(loc,"Cannot find variable '"+cl+"."+loc.getId()+"'");
				}
			}
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
				addError(loc,"Incorrect type of array '"+loc.getId()+"'. Found Var, Expected Array");
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
			addError(loc,"Cannot find array '"+loc.getId()+"'");
		}
		return res;
	}

	@Override
	public Boolean visit(SubClassArrayLocation loc){
		Boolean res=true;
		String cl="";
		List<String> classes = loc.getClasses();
		if (classes.size()>1) {
			res=false;
			addError(loc,"Navigation not allowed");
		}
		else {
			VarLocation varloc = new VarLocation(classes.get(0)); //Search for local declaration
			if (varloc.accept(this)) {
				// Now search for public attribute
				cl = varloc.getType(); //Get class name
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
							addError(loc,"Incorrect type of array '"+cl+"."+loc.getId()+"'. Found Var, Expected Array");					
						}
					}
				}
				else {
					res=false;
					//Not found location
					addError(loc,"Cannot find array '"+cl+"."+loc.getId()+"'");
				}
			}
		}
		return res;
	}

}