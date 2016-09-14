package ir;

import ir.ast.*;

// Abstract visitor
public interface ASTVisitor<T> {
// Visit declarations
	T visit(Program dec);
	T visit(ClassDecl dec);
	T visit(FieldDecl dec);
	T visit(MethodDecl dec);
	T visit(VarDecl dec);	
	T visit(ArrayDecl dec);	
	T visit(FormalParam dec);
	T visit(Body dec);

// visit statements
	T visit(AssignStmt stmt);
	T visit(ReturnStmt stmt);
	T visit(IfStmt stmt);
	T visit(BreakStmt stmt);
	T visit(ContinueStmt stmt);
	T visit(EmptyStmt stmt);
	T visit(ForStmt stmt);
	T visit(WhileStmt stmt);
	T visit(MethodCallStmt stmt);
	T visit(Block stmt);

// visit expressions
	T visit(BinAndExpr expr);
	T visit(BinDivideExpr expr);
	T visit(BinEqualExpr expr);
	T visit(BinGOETExpr expr);
	T visit(BinGTExpr expr);
	T visit(BinLOETExpr expr);
	T visit(BinLTExpr expr);
	T visit(BinMinusExpr expr);
	T visit(BinModExpr expr);
	T visit(BinNotEqualExpr expr);
	T visit(BinOpExpr expr);
	T visit(BinOrExpr expr);
	T visit(BinPlusExpr expr);
	T visit(BinTimesExpr expr);
	T visit(BracketExpr expr);
	T visit(MethodCallExpr expr);
	T visit(MinusExpr expr);	
	T visit(NegatedExpr expr);
	
// visit literals	
	T visit(IntLiteral lit);
	T visit(FloatLiteral lit);
	T visit(BoolLiteral lit);

// visit locations	
	T visit(VarLocation loc);
	T visit(SubClassVarLocation loc);
	T visit(ArrayLocation loc);
	T visit(SubClassArrayLocation loc);
}
