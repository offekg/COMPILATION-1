package AST;

public class AST_PROGRAM extends AST_Node
{
	public AST_DEC_LIST decList;
	
	public AST_PROGRAM(AST_DEC_LIST decList) {
		this.decList = decList;
		SerialNumber = AST_Node_Serial_Number.getFresh();
	}
}