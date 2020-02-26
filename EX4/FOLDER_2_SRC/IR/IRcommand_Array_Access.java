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
		//need to check index is valid
		TEMP arrayPointer = TEMP_FACTORY.getInstance().getFreshTEMP(); //how to use s registers?
		sir_MIPS_a_lot.getInstance().move(arrayPointer,subscriptTemp);
		sir_MIPS_a_lot.getInstance().addi(arrayPointer, arrayPointer, 1);
		TEMP multiplier = TEMP_FACTORY.getInstance().getFreshTEMP();
		sir_MIPS_a_lot.getInstance().li(multiplier, 4);
		sir_MIPS_a_lot.getInstance().mul(arrayPointer, arrayPointer, multiplier);
		
	}

	@Override
	public void printMe() {
		System.out.println(dst.getSymbol() + " = array_access " + arrTemp.getSymbol()
							+ ", " + subscriptTemp.getSymbol());
	}
}
