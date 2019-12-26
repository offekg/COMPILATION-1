package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_STMT_VARDEC extends AST_STMT {
	public AST_DEC_VARDEC vardec;

	public AST_STMT_VARDEC(AST_DEC_VARDEC vardec) {
		this.vardec = vardec;
		SerialNumber = AST_Node_Serial_Number.getFresh();
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe() {
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE STMT VARDEC\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (vardec != null)
			vardec.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "STMT\n->VARDEC\n");

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (vardec != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, vardec.SerialNumber);
	}

	public TYPE SemantMe() {
		return vardec.SemantMe();
	}
}
