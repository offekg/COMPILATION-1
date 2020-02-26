package AST;

import TYPES.*;
import UTILS.Context;
import IR.IR;
import IR.IRcommand_Call_Global_Function;
import IR.IRcommand_Call_Virtual_Function;
import IR.IRcommand_LoadReturnValue;
import IR.IRcommand_PrintInt;
import IR.IRcommand_PrintString;
import IR.IRcommand_Push;
import SYMBOL_TABLE.*;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;

public class AST_EXP_FUNC_CALL extends AST_EXP {
	public AST_VAR var;
	public String funcName;
	public AST_EXP_LIST expList;
	public boolean isGlobal;

	public AST_EXP_FUNC_CALL(AST_VAR var, String funcName, AST_EXP_LIST expList) {
		this.var = var;
		this.funcName = funcName;
		this.expList = expList;
		SerialNumber = AST_Node_Serial_Number.getFresh();
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe() {
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE EXP ID\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (var != null)
			var.PrintMe();
		if (expList != null)
			expList.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "EXP\nID\n");

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (var != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
		if (expList != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, expList.SerialNumber);
	}

	public TYPE SemantMe() {
		TYPE varType = null;
		TYPE funcType = null;
		TYPE_LIST expTypeList = null;

		if (this.var != null)
			varType = this.var.SemantMe();
		if (this.expList != null)
			expTypeList = this.expList.SemantMe();

		if (this.var == null) {
			// either in the same scope or in global scope
			TYPE_FOR_SCOPE_BOUNDARIES currentClassBoundary = SYMBOL_TABLE.getInstance()
					.getLastScopeOfType(ScopeType.CLASS_SCOPE);
			if (currentClassBoundary != null) {
				String currentClassName = currentClassBoundary.name;
				TYPE classNode = SYMBOL_TABLE.getInstance().find(currentClassName);
				funcType = ((TYPE_CLASS) classNode).getOverriddenMethod(funcName);
			}
			if (funcType == null) {
				funcType = SYMBOL_TABLE.getInstance().find(funcName);
				this.isGlobal = true;
			}
		} else if (varType.isClass()) {
			// check if the function is declared in the type's class
			funcType = ((TYPE_CLASS) varType).getOverriddenMethod(funcName);
		} else
			// varType is not a class
			OutputFileWriter.writeError(this.lineNumber,
					String.format("tried calling method %s from undeclared class.\n", funcName));

		if (funcType == null)
			OutputFileWriter.writeError(this.lineNumber, String.format("function is not declared %s\n", funcName));

		if (!isFunctionCallValid((TYPE_FUNCTION) funcType, expTypeList))
			OutputFileWriter.writeError(this.lineNumber,
					String.format("exp_func_call function call is not valid %s\n", funcName));

		return ((TYPE_FUNCTION) funcType).returnType;
	}

	public boolean isFunctionCallValid(TYPE_FUNCTION funcType, TYPE_LIST argsTypeList) {
		TYPE funcArg;
		TYPE callArg;

		TYPE_LIST funcArgsType = funcType.paramTypes;

		while (funcArgsType != null && argsTypeList != null) {
			funcArg = funcArgsType.head;
			callArg = argsTypeList.head;

			if (!callArg.equalsOrSubclass(funcArg))
				return false;

			funcArgsType = funcArgsType.tail;
			argsTypeList = argsTypeList.tail;
		}

		if (funcArgsType != null || argsTypeList != null)
			return false;

		return true;
	}

	public TEMP IRme() {
		TEMP t = null;
		if (funcName.equals("PrintInt")) {
			if (expList != null) {
				t = expList.IRme();
			}
			IR.getInstance().Add_IRcommand(new IRcommand_PrintInt(t));

			return null;
		}
		if (funcName.equals("PrintString")) {
			if (expList != null) {
				t = expList.IRme();
			}
			IR.getInstance().Add_IRcommand(new IRcommand_PrintString(t));

			return null;
		}

		// push all expList to stack
		AST_EXP_LIST cur = expList;
		TEMP t2;
		while (cur != null) {
			t2 = cur.head.IRme();
			IR.getInstance().Add_IRcommand(new IRcommand_Push(t2));
			cur = cur.tail;
		}

		// //push return address
		// IR.getInstance().Add_IRcommand(new IRcommand_Push());
		if (this.var != null) {
			TEMP temp = this.var.IRme();
			IR.getInstance().Add_IRcommand(new IRcommand_Call_Virtual_Function(temp, this.funcName));
			TEMP returnTemp = TEMP_FACTORY.getInstance().getFreshTEMP();
			IR.getInstance().Add_IRcommand(new IRcommand_LoadReturnValue(returnTemp));
			return returnTemp;
		}
		if (!this.isGlobal) {
			TEMP currentObject = Context.currentObject;
			IR.getInstance().Add_IRcommand(new IRcommand_Call_Virtual_Function(currentObject, this.funcName));
			TEMP returnTemp = TEMP_FACTORY.getInstance().getFreshTEMP();
			IR.getInstance().Add_IRcommand(new IRcommand_LoadReturnValue(returnTemp));
			return returnTemp;
		}
		IR.getInstance().Add_IRcommand(new IRcommand_Call_Global_Function(this.funcName));
		TEMP returnTemp = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR.getInstance().Add_IRcommand(new IRcommand_LoadReturnValue(returnTemp));
		return returnTemp;
	}
}