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
		// check if type exists
		if (typeOfParam == null) {
			OutputFileWriter.writeError(this.lineNumber, String.format("func_var type not declared: %s", paramType));
		}
		// check if name already exists
		if (SYMBOL_TABLE.getInstance().isInScope(paramName)) {
			OutputFileWriter.writeError(this.lineNumber,
					String.format("func_var name already exists in func %s", paramName));
		}

		SYMBOL_TABLE.getInstance().enter(paramName, typeOfParam);

		return typeOfParam;
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