package AST;

import TYPES.*;
import IR.IR;
import IR.IRcommandConstInt;
import SYMBOL_TABLE.*;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;

public class AST_EXP_MINUS extends AST_EXP {
	public int i;

	public AST_EXP_MINUS(int i) {
		this.i = i;
		SerialNumber = AST_Node_Serial_Number.getFresh();
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe() {
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE EXP MINUS\n");

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "EXP\nMINUS\n");
	}

	public TYPE SemantMe() {
		return TYPE_INT.getInstance();
	}
	
	public TEMP IRme() {
		TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR.getInstance().Add_IRcommand(new IRcommandConstInt(t,-i));
		return t;
	}

}
