package AST;

public class AST_NEWEXP_ID2 extends AST_NEWEXP {
	String name;
	AST_EXP exp;
	public AST_NEWEXP_ID2(String name, AST_EXP exp) {
		this.name = name;
		this.exp = exp;
		SerialNumber = AST_Node_Serial_Number.getFresh();
	}
}
