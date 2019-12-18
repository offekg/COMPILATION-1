package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_EXP_MINUS extends AST_EXP {
	public int i;
	public AST_EXP_MINUS(int i) {
		this.i = i;
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
		System.out.print("AST NODE EXP MINUS\n");

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"EXP\nMINUS\n");
	}
}
