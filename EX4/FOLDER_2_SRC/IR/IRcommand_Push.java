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
	}
	
	@Override
	public void printMe() {
		System.out.println("push_to_stack " + temp.getSymbol());
	}
}
