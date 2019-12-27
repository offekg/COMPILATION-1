package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_DEC_VARDEC_NEW extends AST_DEC_VARDEC {
	public String type;
	public AST_NEWEXP newExp;

	public AST_DEC_VARDEC_NEW(String type, String name, AST_NEWEXP newExp) {
		this.type = type;
		this.name = name;
		this.newExp = newExp;
		SerialNumber = AST_Node_Serial_Number.getFresh();
	}

	public TYPE SemantMe() {
		TYPE declaredType;

		/****************************/
		/* [1] Check If Type exists */
		/****************************/
		declaredType = SYMBOL_TABLE.getInstance().find(type);
		if (declaredType == null) {
			OutputFileWriter.writeError(this.lineNumber, String.format("non existing type %s\n", type));
		}

		/**************************************/
		/* [2] Check That Name does NOT exist */
		/**************************************/
		if (SYMBOL_TABLE.getInstance().isInScope(name)) {
			OutputFileWriter.writeError(this.lineNumber, String.format("variable %s already exists in scope\n", name));
		}

		// Check that the new instance is of the same type
		if (newExp != null) {
			TYPE assignmentType = newExp.SemantMe();
			if (assignmentType == null) {
				OutputFileWriter.writeError(this.lineNumber, String.format("could not resolve assignment\n"));
			}
			if (declaredType.isClass()) {
				if (!TYPE_CLASS.isSubClassOf(assignmentType, declaredType)) {
					OutputFileWriter.writeError(this.lineNumber, "class type mismatch for var := new exp\n");
				}
			} else if (declaredType.isArray()) {
				TYPE_ARRAY arrayDT = (TYPE_ARRAY) declaredType;
				if (assignmentType != arrayDT.arrayType) {
					OutputFileWriter.writeError(this.lineNumber, "array type mismatch for var := new exp\n");
				}
			} else {
				OutputFileWriter.writeError(this.lineNumber, "tried to assign a non array/class type with NEW\n");
			}
		}

		/***************************************************/
		/* [3] Enter the Function Type to the Symbol Table */
		/***************************************************/
		SYMBOL_TABLE.getInstance().enter(name, declaredType);

		/*********************************************************/
		/* [4] Return value is irrelevant for class declarations */
		/*********************************************************/
		return declaredType;
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe() {
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE VAR DEC NEW\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (newExp != null)
			newExp.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "VAR DEC\nNEW\n");

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (newExp != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, newExp.SerialNumber);
	}
}
