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

public class IRcommand_Function_Epilogue extends IRcommand {
	String funcName;

	public IRcommand_Function_Epilogue(String funcName) {
		this.funcName = funcName;
	}

	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme() {
		String funcEndLabel = IRcommand.getFreshLabel(funcName + "_epilogue");
		sir_MIPS_a_lot.getInstance().function_epilogue(funcEndLabel,funcName);
	}
	
	@Override
	public void printMe() {
		System.out.println("function_epilogue");
	}
}
