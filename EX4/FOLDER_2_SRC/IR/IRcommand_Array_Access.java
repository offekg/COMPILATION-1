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
		//check index is valid
		String abort_label = "Access_Violation";
		sir_MIPS_a_lot.getInstance().bltz(subscriptTemp,abort_label);
		TEMP arraySize = TEMP_FACTORY.getInstance().getFreshTEMP();
		// add check that array adrdress is not null
		sir_MIPS_a_lot.getInstance().beqz(arrTemp, "Invalid_Ptr_Dref");
		//check array index < array size
		sir_MIPS_a_lot.getInstance().lw(arraySize,arrTemp,0);
		sir_MIPS_a_lot.getInstance().bge(subscriptTemp,arraySize,abort_label);

		
		//get pointer to wanted cell
		TEMP arrayPointer = TEMP_FACTORY.getInstance().getFreshTEMP(); //how to use s registers?
		sir_MIPS_a_lot.getInstance().move(arrayPointer,subscriptTemp);
		sir_MIPS_a_lot.getInstance().addi(arrayPointer, arrayPointer, 1);
		sir_MIPS_a_lot.getInstance().sll(arrayPointer, arrayPointer, 2);
		
		sir_MIPS_a_lot.getInstance().add(arrayPointer, arrayPointer, arrTemp);
		sir_MIPS_a_lot.getInstance().lw(dst,arrayPointer,0);
		
	}

	@Override
	public void printMe() {
		System.out.println(dst.getSymbol() + " = array_access " + arrTemp.getSymbol()
							+ ", " + subscriptTemp.getSymbol());
	}
}
