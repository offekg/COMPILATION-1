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

public class IRcommand_LoadLocalVar extends IRcommand {
	TEMP dst;
	String var_name;

	public IRcommand_LoadLocalVar(TEMP dst, String var_name) {
		this.dst = dst;
		this.var_name = var_name;
	}

	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme() {
		
		int var_offset = Context.localFrameVarsList.getLast().get(var_name);
		sir_MIPS_a_lot.getInstance().loadLocalVar(dst, var_offset);
	}
	
	@Override
	public void printMe() {
		System.out.println(dst.getSymbol() + " = " + var_name);
	}
}
