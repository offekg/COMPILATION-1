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

public class IRcommand_Call_Virtual_Function extends IRcommand {
	TEMP temp;
	String funcName;

	public IRcommand_Call_Virtual_Function(TEMP temp, String funcName) {
		this.temp = temp;
		this.funcName = funcName;
	}

	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme() {
	}

	@Override
	public void printMe() {
		System.out.println("virtual_function_call " + temp.getSymbol() + ", " + funcName);
	}
}
