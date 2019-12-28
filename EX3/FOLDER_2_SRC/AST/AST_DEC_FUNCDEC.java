package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_DEC_FUNCDEC extends AST_DEC {
	public String returnType;
	public String funcName;
	public AST_FUNC_INPUT_VARS_LIST params;
	public AST_STMT_LIST funcBody;

	public AST_DEC_FUNCDEC(String returnType, String funcName, AST_FUNC_INPUT_VARS_LIST params,
			AST_STMT_LIST funcBody) {
		this.returnType = returnType;
		this.funcName = funcName;
		this.params = params;
		this.funcBody = funcBody;
		SerialNumber = AST_Node_Serial_Number.getFresh();
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== Dec (FUNC) -> FUNC DEC\n");
	}

	public TYPE SemantMe() {
		/*************************/
		/* [1] Begin Class Scope */
		/*************************/

		// Check return type exists
		TYPE typeOfReturn = SYMBOL_TABLE.getInstance().find(returnType);
		
		if (!(typeOfReturn instanceof TYPE_VOID || typeOfReturn instanceof TYPE_INT
				|| typeOfReturn instanceof TYPE_STRING || typeOfReturn instanceof TYPE_ARRAY)) {
			OutputFileWriter.writeError(this.lineNumber,
					String.format("bad return type dec_funcdec %s %s", returnType, funcName));
		}

		// Check that we are in scope global or CLASS
		ScopeType currentScope = SYMBOL_TABLE.getInstance().getCurrentScopeType();

		if (currentScope != ScopeType.GLOBAL_SCOPE && currentScope != ScopeType.CLASS_SCOPE)
			OutputFileWriter.writeError(this.lineNumber, String.format("not global or class scope dec_funcdec %s %s %s",
					returnType, funcName, String.valueOf(currentScope)));

		// check that no other function/class/etc has the same name
		TYPE typeOfName = SYMBOL_TABLE.getInstance().find(funcName);
		if (typeOfName != null)
			OutputFileWriter.writeError(this.lineNumber,
					String.format("duplicate name dec_funcdec %s %s", returnType, funcName));

		SYMBOL_TABLE.getInstance().beginScope(ScopeType.FUNCTION_SCOPE, funcName, typeOfReturn);
		/***************************/
		/* [2] Semant Data Members */
		/***************************/
		TYPE_FUNCTION t;
		if (params != null) {
			t = new TYPE_FUNCTION(typeOfReturn, this.funcName, params.SemantMe()); // #!@!has null pointer exception
																					// problem
		} else {
			t = new TYPE_FUNCTION(typeOfReturn, this.funcName, null);
		}
		
		SYMBOL_TABLE.getInstance().enter(funcName, t); //for use of the funcBody - for recursion
		funcBody.SemantMe();

		/*****************/
		/* [3] End Scope */
		/*****************/
		SYMBOL_TABLE.getInstance().endScope();

		/************************************************/
		/* [4] Enter the Class Type to the Symbol Table */
		/************************************************/
		SYMBOL_TABLE.getInstance().enter(funcName, t);

		return t;
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe() {
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE FUNC DEC:\n");
		System.out.printf("%s %s()\n", this.returnType, this.funcName);

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (params != null)
			params.PrintMe();
		if (funcBody != null)
			funcBody.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber,
				String.format("FUNC DEC\n %s %s()\n", this.returnType, this.funcName));

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (params != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, params.SerialNumber);
		if (funcBody != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, funcBody.SerialNumber);
	}
}
