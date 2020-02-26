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

public class IRcommand_Array_Access extends IRcommand
{
	public TEMP arrTemp;
	public TEMP subscriptTemp;
	public TEMP dst;
	
	public IRcommand_Array_Access(TEMP dst,TEMP arrTemp,TEMP subscriptTemp)
	{
		this.dst = dst;
		this.arrTemp = arrTemp;
		this.subscriptTemp = subscriptTemp;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
	}
}
