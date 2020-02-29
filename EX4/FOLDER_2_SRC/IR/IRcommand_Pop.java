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

public class IRcommand_Pop extends IRcommand {
	TEMP temp;
	
	public IRcommand_Pop(TEMP temp) {
		this.temp = temp;
	}

	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme() {
		sir_MIPS_a_lot.getInstance().pop(temp);
	}
	
	@Override
	public void printMe() {
		System.out.println(temp.getSymbol() + " = pop_stack");
	}
}
