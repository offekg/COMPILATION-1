/***********/
/* PACKAGE */
/***********/
package IR;

import MIPS.*;
import TEMP.*;
import UTILS.Context;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/

public abstract class IRcommand {
	/*****************/
	/* Label Factory */
	/*****************/
	protected static int label_counter = -1;

	public static String getFreshLabel(String msg) {
		label_counter++;
		while (Context.classNames.contains(String.format("Label_%d", label_counter))) {
			label_counter++;
		}
		return String.format("Label_%d_%s", label_counter, msg);
	}

	/***************/
	/* MIPS me !!! */
	/***************/
	public abstract void MIPSme();

	/* print function for debugging */
	public abstract void printMe();

	public void integerOverflowHandler(TEMP t) {
		TEMP minValue = TEMP_FACTORY.getInstance().getFreshTEMP();
		TEMP maxValue = TEMP_FACTORY.getInstance().getFreshTEMP();

		sir_MIPS_a_lot.getInstance().li(minValue, -32768);
		sir_MIPS_a_lot.getInstance().li(maxValue, 32767);

		String integerValidLabel = getFreshLabel("valid");
		String minOverflowLabel = getFreshLabel("min_overflow");
		String maxOverflowLabel = getFreshLabel("max_overflow");

		sir_MIPS_a_lot.getInstance().bge(minValue, t, minOverflowLabel);
		sir_MIPS_a_lot.getInstance().blt(maxValue, t, maxOverflowLabel);
		sir_MIPS_a_lot.getInstance().jump(integerValidLabel);

		sir_MIPS_a_lot.getInstance().label(minOverflowLabel);
		sir_MIPS_a_lot.getInstance().li(t, -32768);
		sir_MIPS_a_lot.getInstance().jump(integerValidLabel);

		sir_MIPS_a_lot.getInstance().label(maxOverflowLabel);
		sir_MIPS_a_lot.getInstance().li(t, 32767);
		sir_MIPS_a_lot.getInstance().jump(integerValidLabel);

		sir_MIPS_a_lot.getInstance().label(integerValidLabel);
	}

}
