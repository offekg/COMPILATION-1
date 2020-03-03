package AST;

import TYPES.*;
import UTILS.Context;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;

import IR.*;
import SYMBOL_TABLE.*;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;

public class AST_STMT_FUNC_CALL extends AST_STMT {
	public AST_VAR var;
	public String funcName;
	public AST_EXP_LIST args;
	public boolean isGlobal = false;

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

		if (var == null) {
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
		} else
			// check if the function is declared in the type's class
			funcType = ((TYPE_CLASS) varType).getOverriddenMethod(funcName);

		if (funcType == null)
			OutputFileWriter.writeError(this.lineNumber, String.format("function is not declared %s", funcName));

		if (!isFunctionCallValid((TYPE_FUNCTION) funcType, argsTypeList))
			OutputFileWriter.writeError(this.lineNumber,
					String.format("stmt_func_call function call is not valid %s", funcName));

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
			if (args != null) {
				t = args.IRme();
			}
			IR.getInstance().Add_IRcommand(new IRcommand_PrintInt(t));

			return null;
		}
		if (funcName.equals("PrintString")) {
			if (args != null) {
				t = args.IRme();
			}
			IR.getInstance().Add_IRcommand(new IRcommand_PrintString(t));

			return null;
		}

		// //push return address
		// IR.getInstance().Add_IRcommand(new IRcommand_Push());

		int offset;
		if (this.var != null) {
			String className = ((AST_VAR_SIMPLE) var).className;
			offset = findFunctionIndexInVtable(className);

			TEMP temp = this.var.IRme();
			IR.getInstance().Add_IRcommand(new IRcommand_Push(temp));
			pushArgs();
			IR.getInstance().Add_IRcommand(new IRcommand_Call_Virtual_Function(temp, offset));
			return null;
		}
		if (!this.isGlobal) {
			String className = Context.currentClassBuilder;
			offset = findFunctionIndexInVtable(className);
			TEMP currentObject = TEMP_FACTORY.getInstance().getFreshTEMP();
			IR.getInstance().Add_IRcommand(new IRcommand_Get_Input_Var(currentObject, Context.currentObjectIndex));
			IR.getInstance().Add_IRcommand(new IRcommand_Push(currentObject));
			pushArgs();
			IR.getInstance().Add_IRcommand(new IRcommand_Call_Virtual_Function(currentObject, offset));
			return null;
		}
		pushArgs();
		IR.getInstance().Add_IRcommand(new IRcommand_Call_Global_Function(this.funcName));
		return null;
	}
	
	public void pushArgs() {
		// push all args to stack
		AST_EXP_LIST cur = args;
		LinkedList<TEMP> argList = new LinkedList<>();
		while (cur != null) {
			argList.addFirst(cur.head.IRme());
			cur = cur.tail;
		}
		argList.forEach(t1 -> {			
			IR.getInstance().Add_IRcommand(new IRcommand_Push(t1));
		});
	}

	public int findFunctionIndexInVtable(String className) {
		int count = 0;
		LinkedHashMap<String, String> funcs = Context.classMethods.get(className);

		for (String funcNameInVtable : funcs.keySet()) {
			if (funcNameInVtable.equals(this.funcName)) {
				return count;
			}
			count++;
		}

		// garbage return, wont get here.
		return -1;
	}
}
