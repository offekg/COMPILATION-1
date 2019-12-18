package AST;

public class AST_NEWEXP_ID1 extends AST_NEWEXP {
	String name;
	public AST_NEWEXP_ID1(String name) {
		this.name = name;
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
		System.out.print("AST NODE NEW EXP ID1\n");

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"NEWEXP\nID1\n");
	}
}
