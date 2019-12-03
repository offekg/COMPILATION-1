package AST;

public class AST_DEC_VARDEC_NEW extends AST_DEC_VARDEC {
	public String name1;
	public String name2;
	public AST_EXP exp;
	public AST_DEC_VARDEC_NEW(String name1, String name2, AST_NEWEXP newExp) {
		this.name1 = name1;
		this.name2 = name2;
		this.exp = exp;
	}
}
