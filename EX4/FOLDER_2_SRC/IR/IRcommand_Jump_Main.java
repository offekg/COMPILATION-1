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
import UTILS.Context;

public class IRcommand_Jump_Main extends IRcommand
{
	
	public IRcommand_Jump_Main()
	{
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().jump(Context.globalFunctions.get("main"));
	}
	
	@Override
	public void printMe() {
		System.out.println("jump to the program's main function");
	}
}
