package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_STMT_IF extends AST_STMT {
	public AST_EXP cond;
	public AST_STMT_LIST body;

	/*******************/
	/* CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_IF(AST_EXP cond, AST_STMT_LIST body) {
		this.cond = cond;
		this.body = body;
		SerialNumber = AST_Node_Serial_Number.getFresh();
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe() {
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE STMT IF\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (cond != null)
			cond.PrintMe();
		if (body != null)
			body.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "STMT\nIF\n");

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (cond != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, cond.SerialNumber);
		if (body != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, body.SerialNumber);
	}

	public TYPE SemantMe() {
		/****************************/
		/* [0] Semant the Condition */
		/****************************/
		if (cond.SemantMe() != TYPE_INT.getInstance()) {
			OutputFileWriter.writeError(this.lineNumber, "condition inside IF is not an int.\n");
		}

		/*************************/
		/* [1] Begin Class Scope */
		/*************************/
		SYMBOL_TABLE.getInstance().beginScope(ScopeType.IF_WHILE_SCOPE);

		/***************************/
		/* [2] Semant Data Members */
		/***************************/
		body.SemantMe();

		/*****************/
		/* [3] End Scope */
		/*****************/
		SYMBOL_TABLE.getInstance().endScope();

		/*********************************************************/
		/* [4] Return value is irrelevant for class declarations */
		/*********************************************************/
		return null;
	}
}