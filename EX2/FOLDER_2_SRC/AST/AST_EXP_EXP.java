package AST;

public class AST_EXP_EXP extends AST_EXP {
	public AST_EXP exp;

	public AST_EXP_EXP(AST_EXP exp) {
		this.exp = exp;
		SerialNumber = AST_Node_Serial_Number.getFresh();
	}
}
