package AST;

public class AST_NEWEXP_ID1 extends AST_NEWEXP {
	String name;
	public AST_NEWEXP_ID1(String name) {
		this.name = name;
		SerialNumber = AST_Node_Serial_Number.getFresh();
	}
}
