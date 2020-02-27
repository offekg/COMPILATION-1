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

public class IRcommand_LoadGlobal extends IRcommand {
	TEMP dst;
	String var_name;

	public IRcommand_LoadGlobal(TEMP dst, String var_name) {
		this.dst = dst;
		this.var_name = var_name;
	}

	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme() {
		sir_MIPS_a_lot.getInstance().la(dst, var_name);
	}
	
	@Override
	public void printMe() {
		System.out.println(dst.getSymbol() + " = (G)" + var_name);
	}
}
