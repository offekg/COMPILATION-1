package AST;

public class AST_DEC_ARRAY extends AST_DEC {
	public AST_DEC_ARRAYDEC ad;
	
	public AST_DEC_ARRAY(AST_DEC_ARRAYDEC ad) {
		this.ad = ad;
		SerialNumber = AST_Node_Serial_Number.getFresh();
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== Dec -> Dec Array\n");
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST DEC_ARRAY       */
		/**************************************/
		System.out.print("AST NODE (Dec) ARRAY\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (ad != null) ad.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"DEC ARRAY\n");

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (ad != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,ad.SerialNumber);
	}
}
