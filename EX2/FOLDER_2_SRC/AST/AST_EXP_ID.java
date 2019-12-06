package AST;

public class AST_EXP_ID extends AST_EXP {
	public AST_VAR var;
	public String name;
	public AST_EXP exp;
	public AST_EXP_LIST expList;
	public AST_EXP_ID(AST_VAR var, String name, AST_EXP exp,AST_EXP_LIST expList) {
		this.var = var;
		this.name = name;
		this.exp = exp;
		this.expList = expList;
		SerialNumber = AST_Node_Serial_Number.getFresh();
	}
}
