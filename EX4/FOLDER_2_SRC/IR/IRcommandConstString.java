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

public class IRcommandConstString extends IRcommand {
	TEMP t;
	String value;

	public IRcommandConstString(TEMP t, String value) {
		this.t = t;
		this.value = value;
	}

	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme() {
	}
	
	@Override
	public void printMe() {
		System.out.println(t.getSymbol() + " = " + value);
	}
}
