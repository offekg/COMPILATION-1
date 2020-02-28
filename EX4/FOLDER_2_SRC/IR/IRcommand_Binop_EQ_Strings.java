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

public class IRcommand_Binop_EQ_Strings extends IRcommand
{
	public TEMP t1;
	public TEMP t2;
	public TEMP dst;

	public IRcommand_Binop_EQ_Strings(TEMP dst,TEMP t1,TEMP t2)
	{
		this.dst = dst;
		this.t1 = t1;
		this.t2 = t2;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		/*******************************/
		/* [1] Allocate 3 fresh labels */
		/*******************************/
		String label_end        = getFreshLabel("end");
		String label_AssignOne  = getFreshLabel("AssignOne");
		String label_AssignZero = getFreshLabel("AssignZero");
		String label_strCmpLoop = getFreshLabel("strCmpLoop");
		
		/*******************************/
		/* [1.5] Allocate 3 fresh labels */
		/*******************************/
		//temps that go over strings
		TEMP char1 = TEMP_FACTORY.getInstance().getFreshTEMP();
        TEMP char2 = TEMP_FACTORY.getInstance().getFreshTEMP();
        //temps for offset of strings
        TEMP offset1 = TEMP_FACTORY.getInstance().getFreshTEMP();
        TEMP offset2 = TEMP_FACTORY.getInstance().getFreshTEMP();

		
		/******************************************/
		/* [2] compare loop:
		 * 	   if (char1==char2) increase pointers and goto compare loop;  */
		/*     else ( goto label_AssignZero; */
		/******************************************/
        sir_MIPS_a_lot.getInstance().move(offset1, t1);
        sir_MIPS_a_lot.getInstance().move(offset2, t2);
        sir_MIPS_a_lot.getInstance().label(label_strCmpLoop);
        sir_MIPS_a_lot.getInstance().lb(char1, 0, offset1);
        sir_MIPS_a_lot.getInstance().lb(char2, 0, offset2);
        
        //check if chars are equal 
        sir_MIPS_a_lot.getInstance().bne(char1, char1, label_AssignOne); 
        
        //if we saw they are equal, and finished the strings.
        sir_MIPS_a_lot.getInstance().beqz(char1, label_AssignZero);
        
        //increase offsets and jump to beginning of loop
        sir_MIPS_a_lot.getInstance().addi(offset1, offset1, 1);
        sir_MIPS_a_lot.getInstance().addi(offset2, offset2, 1);
        sir_MIPS_a_lot.getInstance().jump(label_strCmpLoop);
		
		/************************/
		/* [3] label_AssignOne: */
		/*                      */
		/*         t3 := 1      */
		/*         goto end;    */
		/*                      */
		/************************/
		sir_MIPS_a_lot.getInstance().label(label_AssignOne);
		sir_MIPS_a_lot.getInstance().li(dst,1);
		sir_MIPS_a_lot.getInstance().jump(label_end);

		/*************************/
		/* [4] label_AssignZero: */
		/*                       */
		/*         t3 := 1       */
		/*         goto end;     */
		/*                       */
		/*************************/
		sir_MIPS_a_lot.getInstance().label(label_AssignZero);
		sir_MIPS_a_lot.getInstance().li(dst,0);
		sir_MIPS_a_lot.getInstance().jump(label_end);

		/******************/
		/* [5] label_end: */
		/******************/
		sir_MIPS_a_lot.getInstance().label(label_end);		
	}
	
	@Override
	public void printMe() {
		System.out.println(dst.getSymbol() + " = eq_strings " + t1.getSymbol()
							+ ", " + t2.getSymbol());
	}
}
