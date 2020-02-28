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

public class IRcommand_Binop_Div_Integers extends IRcommand {
	public TEMP t1;
	public TEMP t2;
	public TEMP dst;

	public IRcommand_Binop_Div_Integers(TEMP dst, TEMP t1, TEMP t2) {
		this.dst = dst;
		this.t1 = t1;
		this.t2 = t2;
	}

	/***************/
	/* MIPS me !!! */
	/***************/

	public void MIPSme() {
		sir_MIPS_a_lot.getInstance().zeroDivisionCheck(t2); 
		sir_MIPS_a_lot.getInstance().div(dst, t1, t2);
		integerOverflowHandler(dst);
	}

	@Override
	public void printMe() {
		System.out.println(dst.getSymbol() + " = div " + t1.getSymbol() + ", " + t2.getSymbol());
	}
}
