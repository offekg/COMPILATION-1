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

public class IRcommand_Array_Set extends IRcommand
{
	TEMP arrTemp;
	TEMP subscriptTemp;
	TEMP value;
	
	public IRcommand_Array_Set(TEMP arrTemp, TEMP subscriptTemp, TEMP value)
	{
		this.arrTemp = arrTemp;
		this.subscriptTemp = subscriptTemp;
		this.value = value;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
	}
}
