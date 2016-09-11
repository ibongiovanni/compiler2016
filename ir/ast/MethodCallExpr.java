package ir.ast;

import ir.ASTVisitor;
import java.util.LinkedList;
import java.util.List;

public class MethodCallExpr extends Expression {
	protected VarLocation method;
	protected List<Expression> params;

	public MethodCallExpr(VarLocation m, List<Expression> ps){
		method = m;
		params = ps;
	}

	public MethodCallExpr(VarLocation m){
		method = m;
		params = new LinkedList<Expression>();
	}

	public VarLocation getMethod(){
		return method;
	}

	public List<Expression> getParams(){
		return params;
	}

	@Override
	public String toString() {
		String rep;
		rep=method.toString()+"(";
		for ( Expression p : params ) {
			rep=rep+p+",";
		}
		rep=rep+")";
		return rep;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

}