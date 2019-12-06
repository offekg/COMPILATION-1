package AST;

public class AST_BINOP extends AST_Node {
	public int OP;
	AST_BINOP(int op){
		this.OP = op;
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();


	}
}
