package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_VAR_SIMPLE extends AST_VAR {
	/************************/
	/* simple variable name */
	/************************/
	public String name;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_SIMPLE(String name) {
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== var -> ID( %s )\n", name);

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.name = name;
	}

	/**************************************************/
	/* The printing message for a simple var AST node */
	/**************************************************/
	public void PrintMe() {
		/**********************************/
		/* AST NODE TYPE = AST SIMPLE VAR */
		/**********************************/
		System.out.format("AST NODE SIMPLE VAR( %s )\n", name);

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("SIMPLE\nVAR\n(%s)", name));
	}

	public TYPE SemantMe() {
		TYPE varType;
		/****************************************/
		/* [1] Check If The var exists in table */
		/****************************************/

		varType = SYMBOL_TABLE.getInstance().find(name);
		if (varType == null) {
			OutputFileWriter.writeError(this.lineNumber, String.format("tried using undeclaired val %s\n", name));
		}
		return varType;

	}
}
