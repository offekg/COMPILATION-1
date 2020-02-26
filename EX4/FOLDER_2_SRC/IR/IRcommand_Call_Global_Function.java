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
	}
	
	@Override
	public void printMe() {
		System.out.println("global_function_call " + funcName);
	}
}
