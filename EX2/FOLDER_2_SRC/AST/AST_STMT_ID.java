package AST;

public class AST_STMT_ID extends AST_STMT {
	public AST_EXP exp;
	public AST_VAR var;
	public String name;
	public AST_EXP_LIST expList;
	public AST_STMT_ID(AST_VAR var,String name, AST_EXP exp, AST_EXP_LIST expList) {
		this.exp = exp;
		this.var = var;
		this.name = name;
		this.expList = expList;
		SerialNumber = AST_Node_Serial_Number.getFresh();
	}
}
