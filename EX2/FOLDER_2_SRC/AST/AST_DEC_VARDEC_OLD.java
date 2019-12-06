package AST;

public class AST_DEC_VARDEC_OLD extends AST_DEC_VARDEC {
	public String name1;
	public String name2;
	public AST_EXP exp;
	public AST_DEC_VARDEC_OLD(String name1, String name2, AST_EXP exp) {
		this.name1 = name1;
		this.name2 = name2;
		this.exp = exp;
		SerialNumber = AST_Node_Serial_Number.getFresh();
	}
}
