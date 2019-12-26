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
			OutputFileWriter.writeError(this.lineNumber,String.format("non existing type %s\n",type));
		}
		/**************************************/
		/* [2] Check That Name does NOT exist */
		/**************************************/
		if (SYMBOL_TABLE.getInstance().isInScope(name)) {
			OutputFileWriter.writeError(this.lineNumber,String.format("variable %s already exists in scope\n",name));
		}
		
		if(exp != null) {
			TYPE assignmentType = exp.SemantMe();
			if (assignmentType == null) {
				OutputFileWriter.writeError(this.lineNumber,String.format("could not resolve assignment type\n"));
			}
			if( !assignmentType.equalsOrSubclass(t)) {
				OutputFileWriter.writeError(this.lineNumber,String.format("variable %s type doesn't fit assignment type\n",name));
			}
		}

		/*****************************************************/
		/* [3] Enter the new Variable in to the Symbol Table */
		/*****************************************************/
		SYMBOL_TABLE.getInstance().enter(name, t);

		/*********************************************************/
		/* [4] Return value is irrelevant for variable declarations */
		/*********************************************************/
		return t;
	}
}
