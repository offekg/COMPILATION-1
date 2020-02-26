package AST;

import TYPES.*;
import IR.IR;
import IR.IRcommandConstInt;
import IR.IRcommandConstString;
import SYMBOL_TABLE.*;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;

public class AST_EXP_STRING extends AST_EXP {
	public String val;

	public AST_EXP_STRING(String val) {
		this.val = val;
		SerialNumber = AST_Node_Serial_Number.getFresh();
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe() {
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE EXP STRING\n");

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "EXP\nSTRING\n");
	}

	public TYPE SemantMe() {
		return TYPE_STRING.getInstance();
	}

	public TEMP IRme() {
		TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR.getInstance().Add_IRcommand(new IRcommandConstString(t,val));
		return t;
	}
}
