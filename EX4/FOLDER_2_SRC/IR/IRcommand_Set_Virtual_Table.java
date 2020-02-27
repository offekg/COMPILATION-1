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

public class IRcommand_Set_Virtual_Table extends IRcommand {
	String className;
	TEMP addr;

	public IRcommand_Set_Virtual_Table(TEMP addr, String className) {
		this.addr = addr;
		this.className = className;
	}

	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme() {
		String classVTlabel = "VT_" + className;
		sir_MIPS_a_lot.getInstance().la(addr, classVTlabel);
	}
	
	@Override
	public void printMe() {
		System.out.println("set_vt " + addr.getSymbol() + ", " + className);
	}
}
