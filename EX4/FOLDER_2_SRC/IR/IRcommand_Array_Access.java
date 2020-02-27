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
		String abort_label = getFreshLabel("bad_index_abort");
		sir_MIPS_a_lot.getInstance().bltz(subscriptTemp,abort_label);
		TEMP arraySize = TEMP_FACTORY.getInstance().getFreshTEMP();
		sir_MIPS_a_lot.getInstance().lw(arraySize,arrTemp,0);
		sir_MIPS_a_lot.getInstance().bge(subscriptTemp,arraySize,abort_label);
		
		//get pointer to wanted cell
		TEMP arrayPointer = TEMP_FACTORY.getInstance().getFreshTEMP(); //how to use s registers?
		sir_MIPS_a_lot.getInstance().move(arrayPointer,subscriptTemp);
		sir_MIPS_a_lot.getInstance().addi(arrayPointer, arrayPointer, 1);
		sir_MIPS_a_lot.getInstance().sll(arrayPointer, arrayPointer, 2);
		
		sir_MIPS_a_lot.getInstance().add(arrayPointer, arrayPointer, arrTemp);
		sir_MIPS_a_lot.getInstance().lw(dst,arrayPointer,0);
		
		sir_MIPS_a_lot.getInstance().label(abort_label);
		TEMP tAbort_msg = TEMP_FACTORY.getInstance().getFreshTEMP();
		sir_MIPS_a_lot.getInstance().la(tAbort_msg, "string_invalid_ptr_dref");
		sir_MIPS_a_lot.getInstance().print_string(tAbort_msg);
		sir_MIPS_a_lot.getInstance().abort();
		
	}

	@Override
	public void printMe() {
		System.out.println(dst.getSymbol() + " = array_access " + arrTemp.getSymbol()
							+ ", " + subscriptTemp.getSymbol());
	}
}
