package AST;

public class AST_EXP_MINUS extends AST_EXP {
	public int i;
	public AST_EXP_MINUS(int i) {
		this.i = i;
		SerialNumber = AST_Node_Serial_Number.getFresh();
	}
}
