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

public class IRcommand_Field_Access extends IRcommand
{
	TEMP instanceAddr;
	int fieldNumber;
	TEMP dest;
	
	public IRcommand_Field_Access(TEMP instanceAddr, int fieldNumber, TEMP dest)
	{
		this.instanceAddr = instanceAddr;
		this.fieldNumber = fieldNumber;
		this.dest = dest;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
	}
}
