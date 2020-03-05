package AST;

import TYPES.*;
import UTILS.Context;
import IR.*;
import SYMBOL_TABLE.*;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;

public class AST_DEC_VARDEC_OLD extends AST_DEC_VARDEC {
	public AST_EXP exp;

	public AST_DEC_VARDEC_OLD(String type, String name, AST_EXP exp) {
		this.type = type;
		this.name = name;
		this.exp = exp;
		SerialNumber = AST_Node_Serial_Number.getFresh();
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe() {
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE VAR DEC OLD\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (exp != null)
			exp.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "VAR DEC\nOLD\n");

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (exp != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
	}

	public TYPE SemantMe() {
		TYPE t;

		/****************************/
		/* [1] Check If Type exists or is VOID */
		/****************************/
		t = SYMBOL_TABLE.getInstance().find(type);
		if (t == null) {
			OutputFileWriter.writeError(this.lineNumber, String.format("non existing type %s\n", type));
		}
		if (t instanceof TYPE_VOID) {
			OutputFileWriter.writeError(this.lineNumber, String.format("illegal decleration of void varriable\n"));
		}
		/**************************************/
		/* [2] Check That Name does NOT exist in scope or as a type */
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
		
		// check if exists as a type
		TYPE temp = SYMBOL_TABLE.getInstance().find(name);
		if (temp != null && !(temp instanceof TYPE_FUNCTION) && temp.name.equals(name)) {
			OutputFileWriter.writeError(this.lineNumber, String.format("variable %s already exists as a type\n", name));
		}

		/**************************************/
		/* [3] Check exp assignment type */
		/**************************************/
		if (exp != null) {
			TYPE assignmentType = exp.SemantMe();
			if (assignmentType == null) {
				OutputFileWriter.writeError(this.lineNumber, String.format("could not resolve assignment type\n"));
			}
			if (!assignmentType.equalsOrSubclass(t)) {
				OutputFileWriter.writeError(this.lineNumber,
						String.format("variable %s type doesn't fit assignment type\n", name));
			}
		}

		/*****************************************************/
		/* [4] Enter the new Variable in to the Symbol Table */
		/*****************************************************/
		SYMBOL_TABLE.getInstance().enter(name, t);

		/*********************************************************/
		/* [5] Return value is irrelevant for variable declarations */
		/*********************************************************/
		Context.varsInFunc += 1;
		return t;
	}
	
	public TEMP IRme() {
		TEMP t;
		if(exp != null)
			t = exp.IRme();
		else{
			t = TEMP_FACTORY.getInstance().getFreshTEMP();
			IR.getInstance().Add_IRcommand(new IRcommandConstInt(t,0));
		}
		if (this.isGlobal) {
			Context.globals.add(name);
			IR.getInstance().Add_IRcommand(new IRcommand_StoreGlobal(name, t));
		} else {			
			Context.varStack.getLast().add(name);
			IR.getInstance().Add_IRcommand(new IRcommand_StoreLocalVar(name, t));
		}
		return t;
	}
}
