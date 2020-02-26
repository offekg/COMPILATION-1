/***********/
/* PACKAGE */
/***********/
package IR;

import UTILS.Context;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/

public abstract class IRcommand
{
	/*****************/
	/* Label Factory */
	/*****************/
	protected static int label_counter=-1;
	public    static String getFreshLabel(String msg)
	{
		label_counter++;
		while (Context.classNames.contains(String.format("Label_%d",label_counter))) {
			label_counter++;
		}
		return String.format("Label_%d_%s",label_counter,msg);
	}

	/***************/
	/* MIPS me !!! */
	/***************/
	public abstract void MIPSme();
	
	/* print function for debugging */
	public abstract void printMe();
}
