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
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== Dec (CLASS) -> CLASS DEC"\n");
	}
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST DEC  CLASS DEC */
		/**************************************/
		if (name2 == null) 
		    System.out.print("AST NODE CLASS DEC:\n Class %s\n",this.name1);
		else  
		    System.out.print("AST NODE CLASS DEC:\n Class %s Extends %s\n",this.name1,this.name2);

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (cFieldList != null) cFieldList.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		if (name2 == null) {
		    AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("CLASS DEC\n Class %s\n",this.name1));
		}
		else  {
		    AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("CLASS DEC\n Class %s Extends %s\n",this.name1,this.name2));
		}
		
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (cFieldList != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,cFieldList.SerialNumber);
	}
}
