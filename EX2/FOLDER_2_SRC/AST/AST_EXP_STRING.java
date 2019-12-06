package AST;

public class AST_EXP_STRING extends AST_EXP {
	public String val;
	public AST_EXP_STRING(String val) {
		this.val = val;
		SerialNumber = AST_Node_Serial_Number.getFresh();
	}
}
