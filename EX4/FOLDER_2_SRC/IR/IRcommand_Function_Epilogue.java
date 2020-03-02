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

import java.util.HashSet;

import MIPS.*;

public class IRcommand_Function_Epilogue extends IRcommand {
	String funcName;
	String epilogueLabel;
	int numParams;

	public IRcommand_Function_Epilogue(String funcName, int numParams) {
		this.funcName = funcName;
		this.epilogueLabel = Context.epilogueLabel;
		this.numParams = numParams;
	}

	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme() {
		sir_MIPS_a_lot.getInstance().function_epilogue(epilogueLabel,funcName);
		Context.localFrameVarsList.removeLast();
	}
	
	@Override
	public void printMe() {
		System.out.println("function_epilogue " + numParams);
	}
}
