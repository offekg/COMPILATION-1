package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_NEWEXP_CLASS extends AST_NEWEXP {
	String name;

	public AST_NEWEXP_CLASS(String name) {
		this.name = name;
		SerialNumber = AST_Node_Serial_Number.getFresh();
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe() {
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE NEW EXP CLASS\n");

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "NEWEXP\nID1\n");
	}

	public TYPE SemantMe() {
		TYPE t;

		/****************************/
		/* [1] Check If Type exists */
		/****************************/
		t = SYMBOL_TABLE.getInstance().find(name);
		if (t == null) {
			System.out.format(">> ERROR [%d:%d] non existing type %s\n", 2, 2, name);
			System.exit(0);
		}
		return t;
	}
}
