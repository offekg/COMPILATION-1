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

public class IRcommand_New_Array extends IRcommand
{
	TEMP dest;
	TEMP size;
	
	public IRcommand_New_Array(TEMP dest,TEMP size)
	{
		this.size = size;
		this.dest = dest;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
	}
	
	@Override
	public void printMe() {
		System.out.println(dest.getSymbol() + " = new_array " + size.getSymbol());
	}
}
