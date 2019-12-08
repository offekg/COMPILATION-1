package AST;

public class AST_DEC_CLASSDEC extends AST_DEC {
	public String name1;
	public String name2;
	public AST_CFIELD_LIST cFieldList;
	public AST_DEC_CLASSDEC(String name1, String name2, AST_CFIELD_LIST cFieldList) {
		this.name1 = name1;
		this.name2 = name2;
		this.cFieldList = cFieldList;
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
		System.out.print("AST NODE CLASS DEC\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (cFieldList != null) cFieldList.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"CLASS\nDEC\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (cFieldList != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,cFieldList.SerialNumber);
	}
}
