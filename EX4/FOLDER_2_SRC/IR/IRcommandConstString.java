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
		String sLabel = Context.stringLabels.get(value);
		sir_MIPS_a_lot.getInstance().la(t, sLabel);
	}
	
	@Override
	public void printMe() {
		System.out.println(t.getSymbol() + " = " + value);
	}
}
