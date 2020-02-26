package AST;

import TYPES.*;
import UTILS.Context;

import java.util.HashMap;

import IR.IR;
import IR.IRcommandConstInt;
import IR.IRcommandConstString;
import MIPS.sir_MIPS_a_lot;
import SYMBOL_TABLE.*;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;

public class AST_EXP_STRING extends AST_EXP {
	public static int numOfHardCodedStrings = 0;
	public String val;
	public String sLabel;

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
		
		if(!Context.globalFunctions.containsKey(val)) {
			sLabel = "Const_String_number_" + numOfHardCodedStrings;
			numOfHardCodedStrings++;
			Context.globalFunctions.put(val, sLabel);
			sir_MIPS_a_lot.add_to_global_data_list(sLabel, ".asciiz", "\"" + val + "\"");
		}
		
		return TYPE_STRING.getInstance();
	}

	public TEMP IRme() {
		TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR.getInstance().Add_IRcommand(new IRcommandConstString(t, val));
		return t;
	}
}
