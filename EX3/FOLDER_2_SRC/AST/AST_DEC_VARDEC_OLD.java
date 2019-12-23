package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_DEC_VARDEC_OLD extends AST_DEC_VARDEC {
	public String type;
	public String name;
	public AST_EXP exp;

	public AST_DEC_VARDEC_OLD(String type, String name, AST_EXP exp) {
		this.type = type;
		this.name = name;
		this.exp = exp;
		SerialNumber = AST_Node_Serial_Number.getFresh();
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe() {
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE VAR DEC OLD\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (exp != null)
			exp.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "VAR DEC\nOLD\n");

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (exp != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
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
		
		if(exp != null) {
			TYPE assignmentType = exp.SemantMe();
			if (assignmentType == null) {
				System.out.format(">> ERROR [%d:%d] could not resolve assignment\n", 2, 2);
				System.exit(0);
			}
			if (t instanceof TYPE_CLASS) {
				if (!TYPE_CLASS.isSubClassOf(assignmentType, t)) {
					System.out.format(">> ERROR [%d:%d] type mismatch for var := exp\n", 6, 6);
					System.exit(0);
				}
			} else if (t != assignmentType) {
				System.out.format(">> ERROR [%d:%d] variable %s type doesn't fit assignment\n", 2, 2, name);
				System.exit(0);
			}
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
}