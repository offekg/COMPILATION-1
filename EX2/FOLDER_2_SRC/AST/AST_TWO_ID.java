package AST;

public class AST_TWO_ID extends AST_Node
{
	public String name1;
	public String name2;

	public AST_TWO_ID(String name1, String name2) {
		this.name1 = name1;
		this.name2 = name2;
		SerialNumber = AST_Node_Serial_Number.getFresh();
	}
}