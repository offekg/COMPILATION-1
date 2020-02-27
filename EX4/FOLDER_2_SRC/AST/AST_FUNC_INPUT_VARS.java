package AST;

import TYPES.*;
import UTILS.Context;
import IR.IR;
import IR.IRcommand_Pop;
import IR.IRcommand_StoreLocalVar;
import SYMBOL_TABLE.*;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;

public class AST_FUNC_INPUT_VARS extends AST_Node {
	public String paramType;
	public String paramName;

	public AST_FUNC_INPUT_VARS(String paramType, String paramName) {
		this.paramType = paramType;
		this.paramName = paramName;
		SerialNumber = AST_Node_Serial_Number.getFresh();
	}
	
	@Override
	public TEMP IRme() {
		TEMP paramTemp = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR.getInstance().Add_IRcommand(new IRcommand_Pop(paramTemp));
		Context.varStack.getLast().add(paramName);
		IR.getInstance().Add_IRcommand(new IRcommand_StoreLocalVar(this.paramName, paramTemp));
		return paramTemp;
	}

	public TYPE SemantMe() {
		TYPE typeOfParam = SYMBOL_TABLE.getInstance().find(paramType);
		// check if type exists or is illegal
		if (typeOfParam == null) {
			OutputFileWriter.writeError(this.lineNumber, String.format("func_var type not declared: %s", paramType));
		}
		if (typeOfParam instanceof TYPE_VOID || typeOfParam instanceof TYPE_FUNCTION 
				|| typeOfParam instanceof TYPE_NILL) {
			OutputFileWriter.writeError(this.lineNumber, String.format("func_var type not legal: %s", paramType));
		}
		
		// check if name already exists in other params 
		if (SYMBOL_TABLE.getInstance().isInScope(paramName)) {
			OutputFileWriter.writeError(this.lineNumber,
					String.format("func_var name already exists in func %s", paramName));
		}
		//check if name exists as a type
		TYPE temp = SYMBOL_TABLE.getInstance().find(paramName);
		if (temp != null && temp.name.equals(paramName) ) {
			OutputFileWriter.writeError(this.lineNumber, String.format("param %s already exists as a type\n", paramName));
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