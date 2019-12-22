package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_DEC_VARDEC_NEW extends AST_DEC_VARDEC {
	public String type;
	public String name;
	public AST_NEWEXP newExp;
	public AST_DEC_VARDEC_NEW(String type, String name, AST_NEWEXP newExp) {
		this.type = type;
		this.name = name;
		this.newExp = newExp;
		SerialNumber = AST_Node_Serial_Number.getFresh();
	}
	
	public TYPE SemantMe() {
		TYPE t;

		/****************************/
		/* [1] Check If Type exists */
		/****************************/
		t = SYMBOL_TABLE.getInstance().find(type);
		if (t == null) {
			System.out.format(">> ERROR [%d:%d] non existing type %s\n", 2, 2, type);
			System.exit(0);
		}

		/**************************************/
		/* [2] Check That Name does NOT exist */
		/**************************************/
		if (SYMBOL_TABLE.getInstance().isInScope(name)) {
			System.out.format(">> ERROR [%d:%d] variable %s already exists in scope\n", 2, 2, name);
			System.exit(0);
		}
		
		if(newExp != null && t != newExp.SemantMe()) {
			System.out.format(">> ERROR [%d:%d] variable %s type doesn't fit assignment\n", 2, 2, name);
			System.exit(0);
		}

		/***************************************************/
		/* [3] Enter the Function Type to the Symbol Table */
		/***************************************************/
		SYMBOL_TABLE.getInstance().enter(name, t);

		/*********************************************************/
		/* [4] Return value is irrelevant for class declarations */
		/*********************************************************/
		return t;
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE VAR DEC NEW\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (newExp != null) newExp.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"VAR DEC\nNEW\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (newExp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,newExp.SerialNumber);
	}
}
