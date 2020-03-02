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

public class IRcommand_Get_Input_Var extends IRcommand {
	TEMP temp;
	int index;
	
	public IRcommand_Get_Input_Var(TEMP temp, int index) {
		this.temp = temp;
		this.index = index;
	}

	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme() {
		sir_MIPS_a_lot.getInstance().loadFuncParam(temp, index);
	}
	
	@Override
	public void printMe() {
		System.out.println(temp.getSymbol() + " = get_input_var " + this.index);
	}
}
