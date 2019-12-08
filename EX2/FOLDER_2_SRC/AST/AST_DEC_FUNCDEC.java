package AST;

public class AST_DEC_FUNCDEC extends AST_DEC {
	public String name1;
	public String name2;
	public String name3;
	public String name4;
	public AST_TWO_ID_LIST twoIdList;
	public AST_STMT_LIST stmtList;
	public AST_DEC_FUNCDEC(String name1, String name2, 
	String name3, String name4, AST_TWO_ID_LIST twoIdList, AST_STMT_LIST stmtList) {
		this.name1 = name1;
		this.name2 = name2;	
		this.name3 = name3;
		this.name4 = name4;	
		this.twoIdList = twoIdList;
		this.stmtList = stmtList;
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
		System.out.print("AST NODE FUNC DEC\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (twoIdList != null) twoIdList.PrintMe();
		if (stmtList != null) stmtList.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"FUNC\nDEC\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (twoIdList != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,twoIdList.SerialNumber);
		if (stmtList != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,stmtList.SerialNumber);
	}
}
