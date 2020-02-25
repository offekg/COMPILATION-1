package AST;

import TYPES.*;
import IR.IR;
import IR.IRcommand_Call_Global_Function;
import IR.IRcommand_LoadReturnValue;
import IR.IRcommand_New_Array;
import SYMBOL_TABLE.*;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;

public class AST_NEWEXP_CLASS extends AST_NEWEXP {
	String expType;

	public AST_NEWEXP_CLASS(String expType) {
		this.expType = expType;
		SerialNumber = AST_Node_Serial_Number.getFresh();
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe() {
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE NEW EXP CLASS\n");

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("NEWEXP\nCLASS\n%s\n", expType));
	}

	public TYPE SemantMe() {
		TYPE t;

		/****************************/
		/* [1] Check If Type exists */
		/****************************/
		t = SYMBOL_TABLE.getInstance().find(expType);
		if (t == null) {
			OutputFileWriter.writeError(this.lineNumber, String.format("non existing type assignment %s\n", expType));
		}
		return t;
	}
	
	public TEMP IRme() {
		TEMP dest = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR.getInstance().Add_IRcommand(new IRcommand_Call_Global_Function(expType + "_constructor"));
		IR.getInstance().Add_IRcommand(new IRcommand_LoadReturnValue(dest));
		return dest;
	}
}
