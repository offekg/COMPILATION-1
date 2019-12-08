package AST;

public class AST_DEC_CLASS extends AST_DEC {
	public AST_DEC_CLASSDEC cd;
	public AST_DEC_CLASS(AST_DEC_CLASSDEC cd) {
		this.cd = cd;
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
		System.out.print("AST NODE CLASS\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (cd != null) cd.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"CLASS\n");

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (cd != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,cd.SerialNumber);
	}
}