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

public class IRcommand_Call_Virtual_Function extends IRcommand {
	TEMP object;
	int offset;

	public IRcommand_Call_Virtual_Function(TEMP temp, int offset) {
		this.object = temp;
		this.offset = offset;
	}

	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme() {
		// check object is initialized. should it be here ? 
		sir_MIPS_a_lot.getInstance().objectInitializedCheck(object);
        TEMP func_label_address = TEMP_FACTORY.getInstance().getFreshTEMP();
        sir_MIPS_a_lot.getInstance().lw(func_label_address, object, 0);
        sir_MIPS_a_lot.getInstance().addi(func_label_address,func_label_address,4*offset);
        TEMP func_address = TEMP_FACTORY.getInstance().getFreshTEMP();
        sir_MIPS_a_lot.getInstance().lw(func_address, func_label_address, 0);
        sir_MIPS_a_lot.getInstance().jal(func_address);
        //sir_MIPS_a_lot.getInstance().pop(??); need to pop out the object?
	}

	@Override
	public void printMe() {
		System.out.println("virtual_function_call " + object.getSymbol() + ", " + offset);
	}
}
