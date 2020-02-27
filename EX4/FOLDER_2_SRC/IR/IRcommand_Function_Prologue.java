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

public class IRcommand_Function_Prologue extends IRcommand {

	public IRcommand_Function_Prologue() {
	}

	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme() {
		sir_MIPS_a_lot.getInstance().function_prolog();
	}

	@Override
	public void printMe() {
		System.out.println("function_prologue");
	}
}
