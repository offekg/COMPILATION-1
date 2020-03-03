package AST;

import TYPES.*;
import SYMBOL_TABLE.*;
import TEMP.TEMP;

public class AST_CFIELD_LIST extends AST_Node {
	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_CFIELD head;
	public AST_CFIELD_LIST tail;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_CFIELD_LIST(AST_CFIELD head, AST_CFIELD_LIST tail) {
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (tail != null)
			System.out.print("====================== cFieldList -> cField cFieldList\n");
		if (tail == null)
			System.out.print("====================== cFieldList -> cField      \n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.head = head;
		this.tail = tail;
	}
	
	public void setDefaultValues(TEMP instanceAddr) {
		head.setDefaultValue(instanceAddr);
		if (tail != null) {
			tail.setDefaultValues(instanceAddr);
		}
	}

	public TYPE_CLASS_VAR_DEC_LIST SemantMe() {
		if (tail == null) {
			return new TYPE_CLASS_VAR_DEC_LIST(head.SemantMe(), null);
		} else {
			return new TYPE_CLASS_VAR_DEC_LIST(head.SemantMe(), tail.SemantMe());
		}
	}

	/******************************************************/
	/* The printing message for a cField list AST node */
	/******************************************************/
	public void PrintMe() {
		/**************************************/
		/* AST NODE TYPE = AST DECLERATION LIST */
		/**************************************/
		System.out.print("AST NODE cField LIST\n");

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
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "cField\nLIST\n");

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (head != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, head.SerialNumber);
		if (tail != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, tail.SerialNumber);
	}
	
	public TEMP IRme() {
		this.head.IRme();
		return this.tail == null ? null : this.tail.IRme();
	}
}
