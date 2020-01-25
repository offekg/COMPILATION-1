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
	TEMP temp;
	
	public IRcommand_Push(TEMP temp) {
		this.temp = temp;
	}

	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme() {
	}
}
