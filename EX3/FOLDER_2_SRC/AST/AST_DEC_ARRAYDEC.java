package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_DEC_ARRAYDEC extends AST_DEC {
	public String arrayName;
	public String arrayType;
	
	public AST_DEC_ARRAYDEC(String arrayName, String arrayType) {
		this.arrayName = arrayName;
		this.arrayType = arrayType;
		SerialNumber = AST_Node_Serial_Number.getFresh();
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== Dec (Array) -> ARRAY DEC\n");
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST ARRAY DEC      */
		/**************************************/
		System.out.print("AST NODE ARRAY DEC: ARRAY"+ this.arrayName + " = " + this.arrayType + "[]\n");

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("ARRAY DEC\n arr %s = %s[]\n", this.arrayName, this.arrayType));
	}
	
	public TYPE SemantMe() {
		TYPE t;
		TYPE_ARRAY new_type;

		/****************************/
		/* [0] Check If Currently In Global Scope */
		/****************************/
		//to do after deciding on scope type tracking method
		
		/****************************/
		/* [1] Check If Type exists */
		/****************************/
		t = SYMBOL_TABLE.getInstance().find(arrayType);
		if (t == null) {
			System.out.format(">> ERROR [%d:%d] non existing type %s\n", 2, 2, arrayType);
			System.exit(0);
		}

		/*******************************************************/
		/* [2] Check That Name does NOT exist in current scope */
		/*******************************************************/
		if (SYMBOL_TABLE.getInstance().isInScope(arrayName)) {
			System.out.format(">> ERROR [%d:%d] variable %s already exists in scope\n", 2, 2, arrayName);
			System.exit(0);
		}
		

		/***************************************************/
		/* [3] Enter the new Array Type to the Symbol Table*/
		/***************************************************/
		new_type = new TYPE_ARRAY(t,arrayName);
		SYMBOL_TABLE.getInstance().enter(arrayName, new_type);

		/*********************************************************/
		/* [4] Return the new TYPE_ARRAY                                          */
		/*********************************************************/
		return new_type;
	}
}
 
