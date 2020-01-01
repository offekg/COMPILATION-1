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
	public void PrintMe() {
		/**************************************/
		/* AST NODE TYPE = AST ARRAY DEC */
		/**************************************/
		System.out.print("AST NODE ARRAY DEC: ARRAY" + this.arrayName + " = " + this.arrayType + "[]\n");

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber,
				String.format("ARRAY DEC\n arr %s = %s[]\n", this.arrayName, this.arrayType));
	}

	public TYPE SemantMe() {
		TYPE t;
		TYPE_ARRAY new_type;

		/******************************************/
		/* [0] Check If Currently In Global Scope */
		/******************************************/
		if (SYMBOL_TABLE.getInstance().getCurrentScopeType() != ScopeType.GLOBAL_SCOPE) {
			OutputFileWriter.writeError(this.lineNumber, "Array decleration not in global scope\n");
		}

		/****************************/
		/* [1] Check If Type exists and is a TYPE and not just a declared entity */
		/****************************/
		t = SYMBOL_TABLE.getInstance().find(arrayType);
		if (t == null) {
			OutputFileWriter.writeError(this.lineNumber,
					String.format("Array decleration using non existing type %s \n", arrayType));
		}
		
		//
		if (t.name != arrayType) {
			OutputFileWriter.writeError(this.lineNumber,
					String.format("Array decleration using a non TYPE entity: %s \n", arrayType));
		}
		
		/****************************/
		/* [1.5] Check If Type is void */
		/****************************/
		if (t instanceof TYPE_VOID) {
			OutputFileWriter.writeError(this.lineNumber,"Array decleration using void as type is illegal\n");
		}
		/****************************/
		/* [1.5] Check If Type is function */
		/****************************/
		if (t instanceof TYPE_FUNCTION) {
			OutputFileWriter.writeError(this.lineNumber,"Array decleration using function as type is illegal\n");
		}
		
		
		/*******************************************************/
		/* [2] Check That Name does NOT exist  */
		/*******************************************************/
		if (SYMBOL_TABLE.getInstance().find(arrayName) != null) {
			OutputFileWriter.writeError(this.lineNumber,
					String.format("Array decleration variable name %s already exists\n", arrayName));
		}

		/***************************************************/
		/* [3] Enter the new Array Type to the Symbol Table */
		/***************************************************/
		new_type = new TYPE_ARRAY(t, arrayName);
		SYMBOL_TABLE.getInstance().enter(arrayName, new_type);

		/*********************************************************/
		/* [4] Return the new TYPE_ARRAY */
		/*********************************************************/
		return new_type;
	}
}
