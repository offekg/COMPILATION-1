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

public class IRcommand_Field_Set extends IRcommand
{
	TEMP instanceAddr;
	int fieldNumber;
	TEMP value;
	
	public IRcommand_Field_Set(TEMP instanceAddr, int fieldNumber, TEMP value)
	{
		this.instanceAddr = instanceAddr;
		this.fieldNumber = fieldNumber;
		this.value = value;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
	}
	
	@Override
	public void printMe() {
		System.out.println("field_set " + instanceAddr.getSymbol()
							+ ", " + fieldNumber
							+ ", " + value.getSymbol());
	}
}
