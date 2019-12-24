package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_STMT_RETURN extends AST_STMT {
	public AST_EXP returnExp;

	public AST_STMT_RETURN( AST_EXP exp) {
		this.returnExp = exp;
		SerialNumber = AST_Node_Serial_Number.getFresh();
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE STMT RETURN\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (returnExp != null) returnExp.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"STMT\nRETURN\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (returnExp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,returnExp.SerialNumber);
	}
	
	public TYPE SemantMe()
	{
		TYPE_FOR_SCOPE_BOUNDARIES fatherFunc = SYMBOL_TABLE.getInstance().getFunctionScopeType();
		
		//
		if(fatherFunc == null) {
			OutputFileWriter.writeError(this.lineNumber,"Return statemenet out of function scope\n");
		}
		
		//if function declared to return void, make sure there is no return value.
		if(fatherFunc.returnType == TYPE_VOID.getInstance() && this.returnExp != null) {
			OutputFileWriter.writeError(this.lineNumber,"Return statemenet has value, though declaired as void.\n");
		}
		
		//check that actual return type matches functions declared return type
		if(fatherFunc.returnType != this.returnExp.SemantMe()) {
			OutputFileWriter.writeError(this.lineNumber,"Return type missmatch.\n");
		}
		
		//don't care about return value
		return null;
		
	}
	
	
}
