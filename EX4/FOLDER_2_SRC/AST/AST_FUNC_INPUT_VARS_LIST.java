package AST;

import TYPES.*;
import IR.IR;
import IR.IRcommand;
import IR.IRcommand_Pop;
import IR.IRcommand_StoreLocalVar;
import SYMBOL_TABLE.*;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;

public class AST_FUNC_INPUT_VARS_LIST extends AST_Node {
	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_FUNC_INPUT_VARS head;
	public AST_FUNC_INPUT_VARS_LIST tail;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_FUNC_INPUT_VARS_LIST(AST_FUNC_INPUT_VARS head, AST_FUNC_INPUT_VARS_LIST tail) {
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (tail != null)
			System.out.print("====================== twoIDList -> twoID twoIDList\n");
		if (tail == null)
			System.out.print("====================== twoIDList -> twoID \n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.head = head;
		this.tail = tail;
	}
	
	@Override
	public TEMP IRme() {
		this.head.IRme();
		return this.tail == null ? null : this.tail.IRme();
	}

	public TYPE_LIST SemantMe() {
		if (tail == null) {
			return new TYPE_LIST(head.SemantMe(), null);
		} else {
			return new TYPE_LIST(head.SemantMe(), tail.SemantMe());
		}
	}

	/******************************************************/
	/* The printing message for a two ID list AST node */
	/******************************************************/
	public void PrintMe() {
		/**************************************/
		/* AST NODE TYPE = AST DECLERATION LIST */
		/**************************************/
		System.out.print("AST NODE TWO ID LIST\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (head != null)
			head.PrintMe();
		if (tail != null)
			tail.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "TWOID\nLIST\n");

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (head != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, head.SerialNumber);
		if (tail != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, tail.SerialNumber);
	}

}