package AST;

import TYPES.*;
import SYMBOL_TABLE.*;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;
import IR.*;

public class AST_EXP_NIL extends AST_EXP {
	public AST_EXP_NIL() {

	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe() {
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE EXP NIL\n");

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "EXP\nNIL\n");
	}

	public TYPE SemantMe() {
		return TYPE_NILL.getInstance();
	}
	
	public TEMP IRme() {
		TEMP temp = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR.getInstance().Add_IRcommand(new IRcommandConstInt(temp,0));
		return temp;
	}
}
