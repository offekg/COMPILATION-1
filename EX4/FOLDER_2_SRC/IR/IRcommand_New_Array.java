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

public class IRcommand_New_Array extends IRcommand
{
	TEMP dest;
	TEMP size; //num cells wanted
	
	public IRcommand_New_Array(TEMP dest,TEMP size)
	{
		this.size = size;
		this.dest = dest;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().addi(size, size, 1);
		sir_MIPS_a_lot.getInstance().sll(size, size, 2); //mult by 4, as cells are of size 4
		sir_MIPS_a_lot.getInstance().malloc(dest, size);
		// clean the allocated area
		String clean_loop_label = getFreshLabel("Clean_Loop");
		sir_MIPS_a_lot.getInstance().cleanAlloactedMem(dest,size,clean_loop_label);
		
		//return size to original, and store at first cell allocated.
		sir_MIPS_a_lot.getInstance().srl(this.size,this.size,2);
        sir_MIPS_a_lot.getInstance().addi(this.size,this.size,-1);
        sir_MIPS_a_lot.getInstance().sw(dest,size,0);
	}
	
	@Override
	public void printMe() {
		System.out.println(dest.getSymbol() + " = new_array " + size.getSymbol());
	}
}
