package AST;

import TYPES.*;
import UTILS.Context;
import IR.IR;
import IR.IRcommand_StoreLocalVar;
import IR.IRcommand_StoreGlobal;
import SYMBOL_TABLE.*;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;

public class AST_DEC_VARDEC_NEW extends AST_DEC_VARDEC {
	public AST_NEWEXP newExp;

	public AST_DEC_VARDEC_NEW(String type, String name, AST_NEWEXP newExp) {
		this.type = type;
		this.name = name;
		this.newExp = newExp;
		SerialNumber = AST_Node_Serial_Number.getFresh();
	}
	
	@Override
	public TEMP IRme() {
		Context.varStack.getLast().add(name);
		TEMP expTemp = this.newExp.IRme();
		if (this.isGlobal) {
			Context.globals.add(name);
			IR.getInstance().Add_IRcommand(new IRcommand_StoreGlobal(name, expTemp));
		} else {			
			Context.varStack.getLast().add(name);
			IR.getInstance().Add_IRcommand(new IRcommand_StoreLocalVar(name, expTemp));
		}
		return expTemp;
	}

	public TYPE SemantMe() {
		TYPE declaredType;

		/****************************/
		/* [1] Check If Type exists */
		/****************************/
		declaredType = SYMBOL_TABLE.getInstance().find(type);
		if (declaredType == null) {
			OutputFileWriter.writeError(this.lineNumber, String.format("non existing type %s\n", type));
		}

		/**************************************/
		/* [2] Check That Name does NOT exist in scope or as a type*/
		/**************************************/
		TYPE_FOR_SCOPE_BOUNDARIES funcScope = SYMBOL_TABLE.getInstance().getLastScopeOfType(ScopeType.FUNCTION_SCOPE);
		if (funcScope == null) {
			if (SYMBOL_TABLE.getInstance().isInScope(name))
				OutputFileWriter.writeError(this.lineNumber,
						String.format("variable %s already exists in scope\n", name));
		} else {
			if (SYMBOL_TABLE.getInstance().isInScope(name) && !name.equals(funcScope.name)) {
				OutputFileWriter.writeError(this.lineNumber,
						String.format("variable %s already exists in scope\n", name));
			}
		}

		TYPE temp = SYMBOL_TABLE.getInstance().find(name);
		if (temp != null && !(temp instanceof TYPE_FUNCTION) && temp.name.equals(name) ) {
			OutputFileWriter.writeError(this.lineNumber, String.format("variable %s already exists as a type\n", name));
		}

		/**************************************/
		/* [3] Check exp assignment type */
		/**************************************/
		// Check that the new instance is of the same type
		if (newExp != null) {
			TYPE assignmentType = newExp.SemantMe();
			
			if (assignmentType == null) {
				OutputFileWriter.writeError(this.lineNumber, String.format("could not resolve assignment type\n"));
			}
			if (declaredType.isClass()) {
				if (!TYPE_CLASS.isSubClassOf(assignmentType, declaredType)) {
					OutputFileWriter.writeError(this.lineNumber, "class type mismatch for var := new exp\n");
				}
			} else if (declaredType.isArray()) {
				TYPE_ARRAY arrayDT = (TYPE_ARRAY) declaredType;
				if (assignmentType != arrayDT.arrayType) {
					OutputFileWriter.writeError(this.lineNumber, "array type mismatch for var := new exp\n");
				}
			} else {
				OutputFileWriter.writeError(this.lineNumber, "tried to assign a non array/class type with NEW\n");
			}
		}

		/***************************************************/
		/* [4] Enter the Function Type to the Symbol Table */
		/***************************************************/
		SYMBOL_TABLE.getInstance().enter(name, declaredType);

		/*********************************************************/
		/* [5] Return value is irrelevant for class declarations */
		/*********************************************************/
		Context.varsInFunc.add(name);
		return declaredType;
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe() {
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE VAR DEC NEW\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (newExp != null)
			newExp.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "VAR DEC\nNEW\n");

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (newExp != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, newExp.SerialNumber);
	}
}
