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
	TEMP dst;
	TEMP src;
	
	public IRcommand_Move(TEMP dest,TEMP src)
	{
		this.src  = src;
		this.dst = dest;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().move(dst,src);
	}
}
