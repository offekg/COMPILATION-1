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

public class IRcommand_StoreGlobal extends IRcommand
{
	String var_name;
	TEMP src;
	
	public IRcommand_StoreGlobal(String var_name,TEMP src)
	{
		this.src      = src;
		this.var_name = var_name;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		TEMP t1 = TEMP_FACTORY.getInstance().getFreshTEMP();
		String global_label = "global_" + var_name;
		sir_MIPS_a_lot.getInstance().la(t1, global_label);
		sir_MIPS_a_lot.getInstance().sw(src, t1, 0);
	}
	
	@Override
	public void printMe() {
		System.out.println(var_name + "(G) = " + src.getSymbol());
	}
}
