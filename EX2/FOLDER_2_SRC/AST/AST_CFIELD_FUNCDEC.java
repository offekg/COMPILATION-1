package AST;

public abstract class AST_CFIELD_FUNCDEC extends AST_CFIELD {
	public AST_DEC_FUNCDEC funcdec;
	public AST_CFIELD_FUNCDEC(AST_DEC_FUNCDEC funcdec) {
		this.funcdec = funcdec;
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
		System.out.print("AST NODE CFIELD FUNC DEC\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (funcdec != null) funcdec.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"CFIELD\nFUNC DEC\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (funcdec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,funcdec.SerialNumber);
	}
}
