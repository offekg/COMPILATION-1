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
		sir_MIPS_a_lot.getInstance().store(var_name,src);
	}
	
	@Override
	public void printMe() {
		System.out.println(var_name + "(G) = " + src.getSymbol());
	}
}
