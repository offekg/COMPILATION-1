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

public class IRcommand_Field_Access extends IRcommand {
	TEMP instanceAddr;
	int fieldNumber;
	TEMP dest;

	public IRcommand_Field_Access(TEMP instanceAddr, int fieldNumber, TEMP dest) {
		this.instanceAddr = instanceAddr;
		this.fieldNumber = fieldNumber;
		this.dest = dest;
	}

	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme() {
		sir_MIPS_a_lot.getInstance().objectInitializedCheck(instanceAddr);
		//sir_MIPS_a_lot.getInstance().beqz(instanceAddr, "abort");
		// Adding 4 because the first address is the VTable.
		sir_MIPS_a_lot.getInstance().lw(dest, instanceAddr, (fieldNumber * 4) + 4);
	}

	@Override
	public void printMe() {
		System.out.println(dest.getSymbol() + " = field_access " + instanceAddr.getSymbol() + ", " + fieldNumber);
	}
}
