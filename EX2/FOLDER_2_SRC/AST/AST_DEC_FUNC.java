package AST;

public class AST_DEC_FUNC extends AST_DEC {
	public AST_DEC_FUNCDEC fd;
	public AST_DEC_FUNC(AST_DEC_FUNCDEC fd) {
		this.fd = fd;
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
		System.out.print("AST NODE FUNC\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (fd != null) fd.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"FUNC\n");

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (fd != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,fd.SerialNumber);
	}
}