package AST;

import TYPES.*;

public abstract class AST_VAR extends AST_Node {
	public TYPE type;
	public String uniqueId;
	/*********************************************************/
	/* The default message for an unknown AST statement node */
	/*********************************************************/
	public void PrintMe() {
		System.out.print("UNKNOWN AST STATEMENT NODE");
	}

	public TYPE SemantMe() {
		return null;
	}
	
	public boolean isVarSimple() {
		return this instanceof AST_VAR_SIMPLE;
	}
	
	public boolean isVarField() {
		return this instanceof AST_VAR_FIELD;
	}
	
	public boolean isVarSubscrip() {
		return this instanceof AST_VAR_SUBSCRIPT;
	}
}
