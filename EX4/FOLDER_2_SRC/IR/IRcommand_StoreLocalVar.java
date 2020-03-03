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
import UTILS.Context;
import MIPS.*;

public class IRcommand_StoreLocalVar extends IRcommand
{
	String var_name;
	TEMP src;
	
	public IRcommand_StoreLocalVar(String var_name,TEMP src)
	{
		this.src      = src;
		this.var_name = var_name;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		Integer var_offset;
		if(Context.localFrameVarsList.getLast().containsKey(var_name)) { //update to existing local var
			var_offset = Context.localFrameVarsList.getLast().get(var_name);
		}
		else{ //new local var
			var_offset =  Context.localFrameVarsList.getLast().size() + 1;
		}
		Context.localFrameVarsList.getLast().put(var_name, var_offset);
		sir_MIPS_a_lot.getInstance().storeLocalVar(var_offset,src);
	}
	
	@Override
	public void printMe() {
		System.out.println(var_name + " = " + src.getSymbol());
	}
}
