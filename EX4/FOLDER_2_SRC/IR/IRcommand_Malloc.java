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

public class IRcommand_Malloc extends IRcommand
{
	int size;
	TEMP t;
	
	public IRcommand_Malloc(TEMP t, int size)
	{
		this.t = t;
		this.size = size;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		TEMP tSize = TEMP_FACTORY.getInstance().getFreshTEMP();
		sir_MIPS_a_lot.getInstance().li(tSize, size);
		sir_MIPS_a_lot.getInstance().malloc(t,tSize);
	}
	
	@Override
	public void printMe() {
		System.out.println(t.getSymbol() + " = malloc (" + size + ")");
	}
}
