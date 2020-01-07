package AST;

import TYPES.*;

public abstract class AST_EXP extends AST_Node {

	/*********************************************************/
	/* The default message for an unknown AST statement node */
	/*********************************************************/
	public void PrintMe() {
		System.out.print("UNKNOWN AST STATEMENT NODE");
	}

	public TYPE SemantMe() {
		return null;
	}
}