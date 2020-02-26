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

public class IRcommand_Push extends IRcommand {
	TEMP temp = null;
	
	public IRcommand_Push(TEMP temp) {
		this.temp = temp;
	}
	
	//pushes return address
	public IRcommand_Push() {
		
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme() {
		sir_MIPS_a_lot.getInstance().push(temp); // what if null?
	}
}
