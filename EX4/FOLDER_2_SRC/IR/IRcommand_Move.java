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

public class IRcommand_Move extends IRcommand
{
	TEMP dest;
	TEMP src;
	
	public IRcommand_Move(TEMP dest,TEMP src)
	{
		this.src      = src;
		this.dest = dest;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
	}
}
