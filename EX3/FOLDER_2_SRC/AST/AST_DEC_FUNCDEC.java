package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_DEC_FUNCDEC extends AST_DEC {
	public String returnType;
	public String funcName;
	public String firstParamType;
	public String firstParamName;
	public AST_FUNC_INPUT_VARS_LIST otherParamsList;
	public AST_STMT_LIST funcBody;

	public AST_DEC_FUNCDEC(String returnType, String funcName, String firstParamType, String firstParamName,
			AST_FUNC_INPUT_VARS_LIST otherParamsList, AST_STMT_LIST funcBody) {
		this.returnType = returnType;
		this.funcName = funcName;
		this.firstParamType = firstParamType;
		this.firstParamName = firstParamName;
		this.otherParamsList = otherParamsList;
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
		SYMBOL_TABLE.getInstance().beginScope(ScopeType.FUNCTION_SCOPE);

		// Check return type exists
		TYPE typeOfReturn = SYMBOL_TABLE.getInstance().find(returnType);
		if (!(typeOfReturn instanceof TYPE_VOID || typeOfReturn instanceof TYPE_INT
				|| typeOfReturn instanceof TYPE_STRING)) {
			return null;
		}
		/***************************/
		/* [2] Semant Data Members */
		/***************************/
		AST_FUNC_INPUT_VARS firstParam = new AST_FUNC_INPUT_VARS(firstParamType, firstParamName);
		TYPE_FUNCTION t = new TYPE_FUNCTION(this.returnType, this.funcName, firstParam.SemantMe(),
				otherParamsList.SemantMe());

		/*****************/
		/* [3] End Scope */
		/*****************/
		SYMBOL_TABLE.getInstance().endScope();

		/************************************************/
		/* [4] Enter the Class Type to the Symbol Table */
		/************************************************/
		SYMBOL_TABLE.getInstance().enter(funcName, t);

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
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE FUNC DEC:\n");
		System.out.printf("%s %s()\n", this.returnType, this.funcName);
		if (this.firstParamType != null) {
			System.out.printf("params: %s %s\n", this.returnType, this.funcName);
		}

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (otherParamsList != null)
			otherParamsList.PrintMe();
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
		if (otherParamsList != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, otherParamsList.SerialNumber);
		if (funcBody != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, funcBody.SerialNumber);
	}
}
