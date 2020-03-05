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

public class IRcommand_Allocate_Stack extends IRcommand
{
	int numCells;
	
	public IRcommand_Allocate_Stack(int numCells)
	{
		this.numCells = numCells;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().allocate_stack(numCells);
	}

	@Override
	public void printMe() {
		System.out.println("Allocate " + numCells);
	}
}
