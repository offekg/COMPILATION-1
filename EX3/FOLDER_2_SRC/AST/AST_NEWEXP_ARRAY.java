package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_NEWEXP_ARRAY extends AST_NEWEXP {
	String expType;
	AST_EXP sizeExp;
	public AST_NEWEXP_ARRAY(String expType, AST_EXP exp) {
		this.expType = expType;
		this.sizeExp = exp;
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
		System.out.print("AST NODE NEW EXP ARRAY\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (sizeExp != null) sizeExp.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("NEWEXP\nARRAY\n%s\n",expType));

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (sizeExp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,sizeExp.SerialNumber);
	}
	
	public TYPE SemantMe() {
		TYPE t;
		TYPE size;
		//TYPE_ARRAY newArrayType;
		
		/****************************/
		/* [1] Check If Type exists */
		/****************************/
		t = SYMBOL_TABLE.getInstance().find(expType);
		if (t == null) {
			OutputFileWriter.writeError(this.lineNumber,String.format("Non existing type assignment: %s\n",expType));
		}
		
		/*********************************************/
		/* [2] Check If the given size exp is an int */
		/*********************************************/
		size = sizeExp.SemantMe();
		if (size != TYPE_INT.getInstance()) {
			OutputFileWriter.writeError(this.lineNumber,String.format("Array assignment with non integer size\n"));
		}
		
		/*********************************************/
		/* [3] Push new array variable into table    */
		/*********************************************/
		
		///newArrayType = new TYPE_ARRAY(t,"tempName");
		//SYMBOL_TABLE.getInstance().enter(,newArray);
		
		//return the array's type, for checking against the variable it is assigned to.
		return t;
	}
}
