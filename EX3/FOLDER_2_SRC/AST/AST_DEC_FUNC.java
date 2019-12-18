package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_DEC_FUNC extends AST_DEC {
	public AST_DEC_FUNCDEC fd;
	
	public AST_DEC_FUNC(AST_DEC_FUNCDEC fd) {
		this.fd = fd;
		SerialNumber = AST_Node_Serial_Number.getFresh();
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== Dec -> FUNC (DEC)\n");
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST FUNC (DEC)     */
		/**************************************/
		System.out.print("AST NODE DEC (FUNC)\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (fd != null) fd.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"DEC (FUNC)\n");

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (fd != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,fd.SerialNumber);
	}
}