package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_DEC_CLASSDEC extends AST_DEC {
	public String name;
	public String father;
	public AST_CFIELD_LIST cFieldList;

	public AST_DEC_CLASSDEC(String name, String father, AST_CFIELD_LIST cFieldList) {
		this.name = name;
		this.father = father;
		this.cFieldList = cFieldList;
		SerialNumber = AST_Node_Serial_Number.getFresh();
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== Dec (CLASS) -> CLASS DEC\n");
	}

	public TYPE SemantMe() {
		/*************************/
		/* [1] Begin Class Scope */
		/*************************/
		SYMBOL_TABLE.getInstance().beginScope();

		// Check father is a class
		TYPE fatherType = SYMBOL_TABLE.getInstance().find(father);
		if (!(fatherType instanceof TYPE_CLASS)) {
			return null;
		}
		/***************************/
		/* [2] Semant Data Members */
		/***************************/
		TYPE_CLASS t = new TYPE_CLASS(name, (TYPE_CLASS)fatherType, cFieldList.SemantMe());

		/*****************/
		/* [3] End Scope */
		/*****************/
		SYMBOL_TABLE.getInstance().endScope();

		/************************************************/
		/* [4] Enter the Class Type to the Symbol Table */
		/************************************************/
		SYMBOL_TABLE.getInstance().enter(name, t);

		/*********************************************************/
		/* [5] Return value is irrelevant for class declarations */
		/*********************************************************/
		return null;
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe() {
		/**************************************/
		/* AST NODE TYPE = AST DEC CLASS DEC */
		/**************************************/
		if (this.father == null)
			System.out.printf("AST NODE CLASS DEC:\n Class %s\n", this.name);
		else
			System.out.printf("AST NODE CLASS DEC:\n Class %s Extends %s\n", this.name, this.father);

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (cFieldList != null)
			cFieldList.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		if (father == null) {
			AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("CLASS DEC\n Class %s\n", this.name));
		} else {
			AST_GRAPHVIZ.getInstance().logNode(SerialNumber,
					String.format("CLASS DEC\n Class %s Extends %s\n", this.name, this.father));
		}

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (cFieldList != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, cFieldList.SerialNumber);
	}
}
