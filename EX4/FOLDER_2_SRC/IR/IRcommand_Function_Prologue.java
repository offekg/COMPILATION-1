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

import java.util.HashMap;
import java.util.HashSet;

import MIPS.*;

public class IRcommand_Function_Prologue extends IRcommand {
	
	int maxVarCount = 0;
	
	public IRcommand_Function_Prologue(int maxVarCount) {
		this.maxVarCount = maxVarCount;
	}

	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme() {
		Context.localFrameVarsList.addLast(new HashMap<>());
		sir_MIPS_a_lot.getInstance().function_prolog();
		sir_MIPS_a_lot.getInstance().allocate_stack(maxVarCount);
		
	}

	@Override
	public void printMe() {
		System.out.println("function_prologue " + maxVarCount);
	}
}
