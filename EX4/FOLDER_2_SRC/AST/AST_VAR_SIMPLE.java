package AST;

import TYPES.*;
import UTILS.Context;
import SYMBOL_TABLE.*;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;
import IR.*;

public class AST_VAR_SIMPLE extends AST_VAR {
	/************************/
	/* simple variable name */
	/************************/
	public String name;
	public String className;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_SIMPLE(String name) {
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== var -> ID( %s )\n", name);

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.name = name;
	}

	/**************************************************/
	/* The printing message for a simple var AST node */
	/**************************************************/
	public void PrintMe() {
		/**********************************/
		/* AST NODE TYPE = AST SIMPLE VAR */
		/**********************************/
		System.out.format("AST NODE SIMPLE VAR( %s )\n", name);

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("SIMPLE\nVAR\n(%s)", name));
	}

	public TYPE SemantMe() {
		TYPE varType;
		/****************************************/
		/* [1] Check If The var exists in table and receive the correct version of it */
		/****************************************/

		varType = SYMBOL_TABLE.getInstance().varFind(name);
		if (varType == null) {
			OutputFileWriter.writeError(this.lineNumber, String.format("tried using undeclaired val %s\n", name));
		}
		className = varType.name;
		this.type = varType;
		this.uniqueId = SYMBOL_TABLE.getInstance().getUniqueId(name);
		return this.type;
	}

	public TEMP IRme() {
		if (Context.currentClassBuilder != null) {
			if (Context.varStack.getLast().contains(uniqueId)) {
				TEMP temp = TEMP_FACTORY.getInstance().getFreshTEMP();
				IR.getInstance().Add_IRcommand(new IRcommand_LoadLocalVar(temp, uniqueId));
				return temp;
			}
			if (Context.classFields.get(Context.currentClassBuilder).contains(uniqueId)) {
				TEMP objTemp = TEMP_FACTORY.getInstance().getFreshTEMP();
				IR.getInstance().Add_IRcommand(new IRcommand_Get_Input_Var(objTemp, Context.currentObjectIndex));
				return AST_VAR_FIELD.fieldAccessIR(objTemp, Context.currentClassBuilder, uniqueId);
			}
		}
		TEMP temp = TEMP_FACTORY.getInstance().getFreshTEMP();
		if ((!Context.varStack.getLast().contains(uniqueId) || Context.varStack.getLast() == Context.globals)
				&& Context.globals.contains(uniqueId)) {
			IR.getInstance().Add_IRcommand(new IRcommand_LoadGlobal(temp, uniqueId));
			return temp;
		}
		IR.getInstance().Add_IRcommand(new IRcommand_LoadLocalVar(temp, uniqueId));
		return temp;
	}
}
