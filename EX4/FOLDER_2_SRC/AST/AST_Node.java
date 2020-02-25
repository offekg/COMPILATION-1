package AST;

import TEMP.TEMP;
import UTILS.Context;

public abstract class AST_Node {
	/*******************************************/
	/* The serial number is for debug purposes */
	/* In particular, it can help in creating */
	/* a graphviz dot format of the AST ... */
	/*******************************************/
	public int SerialNumber;

	public int lineNumber;

	/***********************************************/
	/* The default message for an unknown AST node */
	/***********************************************/
	public void PrintMe() {
		System.out.print("AST NODE UNKNOWN\n");
	}

	public void setLineNumber(int line) {
		this.lineNumber = line;
	}

	/*****************************************/
	/* The default IR action for an AST node */
	/*****************************************/
	public TEMP IRme() {
		return null;
	}
}
