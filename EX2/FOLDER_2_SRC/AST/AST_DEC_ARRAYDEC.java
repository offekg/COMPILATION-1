package AST;

public class AST_DEC_ARRAYDEC extends AST_DEC {
	public String name1;
	public String name2;
	
	public AST_DEC_ARRAYDEC(String name1, String name2) {
		this.name1 = name1;
		this.name2 = name2;
		SerialNumber = AST_Node_Serial_Number.getFresh();
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== Dec (Array) -> ARRAY DEC\n");
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST ARRAY DEC      */
		/**************************************/
		System.out.print("AST NODE ARRAY DEC: ARRAY"+ this.name1 + " = " + this.name2 + "[]\n");

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("ARRAY DEC\n arr %s = %s[]\n", this.name1, this.name2));
	}
}
 
