package AST;

import TYPES.*;
import UTILS.Context;

import java.util.HashSet;
import java.util.LinkedHashSet;

import IR.*;
import SYMBOL_TABLE.*;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;

public class AST_DEC_FUNCDEC extends AST_DEC {
	public String returnType;
	public String funcName;
	public AST_FUNC_INPUT_VARS_LIST params;
	public AST_STMT_LIST funcBody;
	public int maxVarsCount;

	public AST_DEC_FUNCDEC(String returnType, String funcName, AST_FUNC_INPUT_VARS_LIST params,
			AST_STMT_LIST funcBody) {
		this.returnType = returnType;
		this.funcName = funcName;
		this.params = params;
		this.funcBody = funcBody;
		this.maxVarsCount = 0;
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
		Context.varsInFunc = 0;
		
		// Check return type exists
		TYPE typeOfReturn = SYMBOL_TABLE.getInstance().find(returnType);

		if (typeOfReturn == null) {
			OutputFileWriter.writeError(this.lineNumber,
					String.format("bad return type dec_funcdec %s %s", returnType, funcName));
		}

		// Check that we are in scope global or CLASS
		ScopeType currentScope = SYMBOL_TABLE.getInstance().getCurrentScopeType();

		if (currentScope != ScopeType.GLOBAL_SCOPE && currentScope != ScopeType.CLASS_SCOPE)
			OutputFileWriter.writeError(this.lineNumber, String.format("not global or class scope dec_funcdec %s %s %s",
					returnType, funcName, String.valueOf(currentScope)));

		// check that no other function/class/etc has the same name
		// TYPE typeOfName = SYMBOL_TABLE.getInstance().find(funcName);
		if (SYMBOL_TABLE.getInstance().isInScope(funcName))
			OutputFileWriter.writeError(this.lineNumber,
					String.format("duplicate name dec_funcdec %s %s", returnType, funcName));

		// check that function name doesn't exist as a type
		TYPE temp = SYMBOL_TABLE.getInstance().find(funcName);
		if (temp != null && !(temp instanceof TYPE_FUNCTION) && temp.name.equals(funcName)) {
			OutputFileWriter.writeError(this.lineNumber,
					String.format("wanted function name %s already exists as a type\n", funcName));
		}

		// check also basic primitive functions
		if (temp != null && SYMBOL_TABLE.getInstance().isNameOutsideScopes(funcName)) {
			OutputFileWriter.writeError(this.lineNumber,
					String.format("wanted function name %s already exists as a primitive function\n", funcName));
		}

		SYMBOL_TABLE.getInstance().beginScope(ScopeType.FUNCTION_SCOPE, funcName, typeOfReturn);
		/***************************/
		/* [2] Semant Data Members */
		/***************************/
		TYPE_FUNCTION t;
		if (params != null) {
			t = new TYPE_FUNCTION(typeOfReturn, this.funcName, params.SemantMe()); // #!@!has null pointer exception
																					// problem
		} else {
			t = new TYPE_FUNCTION(typeOfReturn, this.funcName, null);
		}

		SYMBOL_TABLE.getInstance().enter(funcName, t); // for use of the funcBody - for recursion

		funcBody.SemantMe();
		if (!t.isReturnStatemntInside && typeOfReturn != TYPE_VOID.getInstance())
			OutputFileWriter.writeError(this.lineNumber,
					String.format("No return statement for a function that should return %s %s", returnType, funcName));

		maxVarsCount = Context.varsInFunc;
		/*****************/
		/* [3] End Scope */
		/*****************/
		SYMBOL_TABLE.getInstance().endScope();

		/************************************************/
		/* [4] Enter the Class Type to the Symbol Table */
		/************************************************/
		SYMBOL_TABLE.getInstance().enter(funcName, t);

		return t;
	}
	
	//for adding methods to TYPE_CLASS
	public TYPE SemantMe(TYPE_CLASS_VAR_DEC shell) {
		/*************************/
		/* [1] Begin Class Scope */
		/*************************/
		Context.varsInFunc = 0;
		
		// Check return type exists
		TYPE typeOfReturn = SYMBOL_TABLE.getInstance().find(returnType);

		if (typeOfReturn == null) {
			OutputFileWriter.writeError(this.lineNumber,
					String.format("bad return type dec_funcdec %s %s", returnType, funcName));
		}

		// Check that we are in scope global or CLASS
		ScopeType currentScope = SYMBOL_TABLE.getInstance().getCurrentScopeType();

		if (currentScope != ScopeType.GLOBAL_SCOPE && currentScope != ScopeType.CLASS_SCOPE)
			OutputFileWriter.writeError(this.lineNumber, String.format("not global or class scope dec_funcdec %s %s %s",
					returnType, funcName, String.valueOf(currentScope)));

		// check that no other function/class/etc has the same name
		// TYPE typeOfName = SYMBOL_TABLE.getInstance().find(funcName);
		if (SYMBOL_TABLE.getInstance().isInScope(funcName))
			OutputFileWriter.writeError(this.lineNumber,
					String.format("duplicate name dec_funcdec %s %s", returnType, funcName));

		// check that function name doesn't exist as a type
		TYPE temp = SYMBOL_TABLE.getInstance().find(funcName);
		if (temp != null && !(temp instanceof TYPE_FUNCTION) && temp.name.equals(funcName)) {
			OutputFileWriter.writeError(this.lineNumber,
					String.format("wanted function name %s already exists as a type\n", funcName));
		}

		// check also basic primitive functions
		if (temp != null && SYMBOL_TABLE.getInstance().isNameOutsideScopes(funcName)) {
			OutputFileWriter.writeError(this.lineNumber,
					String.format("wanted function name %s already exists as a primitive function\n", funcName));
		}

		SYMBOL_TABLE.getInstance().beginScope(ScopeType.FUNCTION_SCOPE, funcName, typeOfReturn);
		/***************************/
		/* [2] Semant Data Members */
		/***************************/
		shell.name = funcName;
		TYPE_FUNCTION t = (TYPE_FUNCTION) shell.t;
		t.returnType = typeOfReturn;
		t.name = funcName;
		if (params != null) 
			t.paramTypes = params.SemantMe(); // #!@!has null pointer exception

		SYMBOL_TABLE.getInstance().enter(funcName, t); // for use of the funcBody - for recursion

		funcBody.SemantMe();
		if (!t.isReturnStatemntInside && typeOfReturn != TYPE_VOID.getInstance())
			OutputFileWriter.writeError(this.lineNumber,
					String.format("No return statement for a function that should return %s %s", returnType, funcName));

		maxVarsCount = Context.varsInFunc;
		/*****************/
		/* [3] End Scope */
		/*****************/
		SYMBOL_TABLE.getInstance().endScope();

		/************************************************/
		/* [4] Enter the Class Type to the Symbol Table */
		/************************************************/
		SYMBOL_TABLE.getInstance().enter(funcName, t);

		return t;
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

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (params != null)
			params.PrintMe();
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
		if (params != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, params.SerialNumber);
		if (funcBody != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, funcBody.SerialNumber);
	}

	@Override
	public TEMP IRme() {
		String label = "";
		if (Context.currentClassBuilder == null) {
			label = IRcommand.getFreshLabel(this.funcName);
			Context.globalFunctions.put(funcName, label);
		} else {
			label = Context.currentClassBuilder + "_" + funcName;
		}
		Context.epilogueLabel = label + "_epilogue";
		IR.getInstance().Add_IRcommand(new IRcommand_Label(label));
		IR.getInstance().Add_IRcommand(new IRcommand_Function_Prologue(maxVarsCount));
		// Adding a new stack of local variables.
		Context.varStack.addLast(new LinkedHashSet<>());
		if (this.params != null) {
			this.params.IRme();
		}
		int index = Context.varStack.getLast().size();
		if (Context.currentClassBuilder != null) {
			Context.currentObjectIndex = index;
		}
		if (this.funcBody != null) {
			this.funcBody.IRme();
		}
		// Removing the stack added.
		Context.varStack.removeLast();
		IR.getInstance().Add_IRcommand(new IRcommand_Function_Epilogue(funcName, index));
		return null;
	}
}
