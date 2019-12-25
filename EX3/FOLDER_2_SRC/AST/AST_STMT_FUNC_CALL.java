package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_STMT_FUNC_CALL extends AST_STMT {
	public AST_VAR var;
	public String funcName;
	public AST_EXP_LIST args;

	public AST_STMT_FUNC_CALL(AST_VAR var, String funcName, AST_EXP_LIST args) {
		this.var = var;
		this.funcName = funcName;
		this.args = args;
		SerialNumber = AST_Node_Serial_Number.getFresh();
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe() {
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE STMT ID\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (var != null)
			var.PrintMe();
		if (args != null)
			args.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "STMT\nID\n");

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (var != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
		if (args != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, args.SerialNumber);
	}

	public TYPE SemantMe() {
		TYPE varType = null;
		TYPE funcType = null;
		TYPE_LIST argsTypeList = null;

		if (var != null)
			varType = var.SemantMe();
		if (args != null)
			argsTypeList = args.SemantMe();

		if (var == null)
			// either in the same scope or in global scope
			funcType = SYMBOL_TABLE.getInstance().find(funcName);
		else
			// check if the function is declared in the type's class
			funcType = ((TYPE_CLASS) varType).getOveridedMethod(funcName);

		if (funcType == null)
			OutputFileWriter.writeError(this.lineNumber, String.format("function is not declared %s", funcName));

		if (!isFunctionCallValid((TYPE_FUNCTION) funcType, argsTypeList))
			OutputFileWriter.writeError(this.lineNumber, String.format("function call is not valid %s %s", funcName));

		return ((TYPE_FUNCTION) funcType).returnType;
	}

	public boolean isFunctionCallValid(TYPE_FUNCTION funcType, TYPE_LIST argsTypeList) {
		TYPE funcArg;
		TYPE callArg;
		
		TYPE_LIST funcArgsType = funcType.paramTypes;
		
		while (funcArgsType != null && argsTypeList != null) {
			funcArg = funcArgsType.head;
			callArg = argsTypeList.head;
			
			if (funcArg instanceof TYPE_CLASS) {
				if (!TYPE_CLASS.isSubClassOf(funcArg, callArg))
					return false;
			} else if (funcArg != callArg) 
				return false;
			
			funcArgsType = funcArgsType.tail;
			argsTypeList = argsTypeList.tail;
		}
		
		if (funcArgsType != null || argsTypeList != null)
			return false;
		
		return true;
	}
}
