/***********/
/* PACKAGE */
/***********/
package IR;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/

import TEMP.*;
import UTILS.Context;
import MIPS.*;

public class IRcommand_Call_Global_Function extends IRcommand {
	String funcName;

	public IRcommand_Call_Global_Function(String funcName) {
		this.funcName = funcName;
	}

	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme() {
		String func_label = Context.globalFunctions.get(funcName);
		sir_MIPS_a_lot.getInstance().jal(func_label);
	}
	
	@Override
	public void printMe() {
		System.out.println("global_function_call " + funcName);
	}
}
