package AST;

public class AST_STMT_ID extends AST_STMT {
	public AST_EXP exp;

	public AST_STMT_ID(AST_VAR var, AST_EXP exp) {
		this.exp = exp;
		SerialNumber = AST_Node_Serial_Number.getFresh();
	}
}
