package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_FUNC_INPUT_VARS extends AST_Node {
	public String paramType;
	public String paramName;

	public AST_FUNC_INPUT_VARS(String paramType, String paramName) {
		this.paramType = paramType;
		this.paramName = paramName;
		SerialNumber = AST_Node_Serial_Number.getFresh();
	}

	public TYPE SemantMe() {
		TYPE typeOfParam = SYMBOL_TABLE.getInstance().find(paramType);
		if (typeOfParam == null) {
			OutputFileWriter.writeError(this.lineNumber, String.format("func_vars_list %s, %s", paramType, paramName));
		}

		AST_EXP_STRING paramTypeString = new AST_EXP_STRING(paramType);
		AST_EXP_STRING paramNameString = new AST_EXP_STRING(paramName);
		return new TYPE_LIST(paramTypeString.SemantMe(), paramNameString.SemantMe());
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe() {
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE TWO ID\n");

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "TWO\nID\n");
	}
}