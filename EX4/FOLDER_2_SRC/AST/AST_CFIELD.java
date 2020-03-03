package AST;

import TEMP.TEMP;
import TYPES.*;

public abstract class AST_CFIELD extends AST_Node {

	/*********************************************************/
	/* The default message for an unknown AST statement node */
	/*********************************************************/
	public void PrintMe() {
		System.out.print("UNKNOWN AST STATEMENT NODE");
	}

	public TYPE_CLASS_VAR_DEC SemantMe() {
		return null;
	}
	
	public void setDefaultValue(TEMP instanceAddr) {
		return;
	}
}
