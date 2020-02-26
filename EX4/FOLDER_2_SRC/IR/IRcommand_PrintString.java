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

public class IRcommand_PrintString extends IRcommand
{
	TEMP t;
	
	public IRcommand_PrintString(TEMP t)
	{
		this.t = t;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().print_string(t);
	}
	
	@Override
	public void printMe() {
		System.out.println("print_string " + t.getSymbol());
	}
}
