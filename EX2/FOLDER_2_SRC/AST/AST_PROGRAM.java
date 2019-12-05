package AST;

public abstract class AST_PROGRAM extends AST_Node
{
	public AST_DEC_LIST decList;
	public String name2;

	public AST_PROGRAM(AST_DEC_LIST decList) {
		this.decList = decList;
	}
}