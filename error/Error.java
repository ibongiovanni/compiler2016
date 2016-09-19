package error;

public class Error {

	private int line;
	private int col;
	private String msg;

	public Error(int line, int col, String msg){
		this.line=line;
		this.col=col;
		this.msg=msg;
	}

	@Override
	public String toString(){
		return "ERROR, in line: "+line+", column: "+col+". '"+msg+"'";	
	}

}