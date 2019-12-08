package AST;

public class AST_DEC_ARRAYDEC extends AST_DEC {
	public String name1;
	public String name2;
	public AST_DEC_ARRAYDEC(String name1, String name2) {
		this.name1 = name1;
		this.name2 = name2;
		SerialNumber = AST_Node_Serial_Number.getFresh();
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE ARRAY DEC\n");

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"ARRAY\nDEC\n");
	}
}
