package AST;

public class AST_BINOP extends AST_Node {
	public int OP;
	public String sOP="";
	public AST_BINOP(int op){
		this.OP = op;
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		/*********************************/
		/* CONVERT OP to a printable sOP */
		/*********************************/
		if (this.OP == 0) {this.sOP = "+";}
		if (this.OP == 1) {this.sOP = "-";}
		if (this.OP == 2) {this.sOP = "*";}
		if (this.OP == 3) {this.sOP = "/";}
		if (this.OP == 4) {this.sOP = "<";}
		if (this.OP == 5) {this.sOP = ">";}
		if (this.OP == 6) {this.sOP = "=";}
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== BINOP ->"+this.sOP+"\n");
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe()
	{
		
		/**************************************/
		/* AST NODE TYPE = AST BINOP */
		/**************************************/
		System.out.print("AST NODE BINOP\n");

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("BINOP(%s)\n",this.sOP));
	}
}
