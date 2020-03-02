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
		TEMP vt = TEMP_FACTORY.getInstance().getFreshTEMP();
		sir_MIPS_a_lot.getInstance().la(vt, classVTlabel);
		sir_MIPS_a_lot.getInstance().sw(vt, addr, 0);
	}
	
	@Override
	public void printMe() {
		System.out.println("set_vt " + addr.getSymbol() + ", " + className);
	}
}
