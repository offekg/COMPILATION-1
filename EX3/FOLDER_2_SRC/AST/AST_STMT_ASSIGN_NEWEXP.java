package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_STMT_ASSIGN_NEWEXP extends AST_STMT {
	public AST_VAR var;
	public AST_NEWEXP exp;

	public AST_STMT_ASSIGN_NEWEXP(AST_VAR var, AST_NEWEXP exp) {
		this.var = var;
		this.exp = exp;
		SerialNumber = AST_Node_Serial_Number.getFresh();
	}

	/*********************************************************/
	/* The printing message for an assign statement AST node */
	/*********************************************************/
	public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.print("AST NODE STMT ASSIGN NEW EXP\n");

		/***********************************/
		/* RECURSIVELY PRINT VAR + EXP ... */
		/***********************************/
		if (var != null) var.PrintMe();
		if (exp != null) exp.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"ASSIGN\nleft := right\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);
	}
	
	public TYPE SemantMe()
	{
		TYPE t1 = null;
		TYPE t2 = null;
		
		if (var != null) t1 = var.SemantMe();
		if (exp != null) t2 = exp.SemantMe();
		
		if (t1 != t2)
		{
			//TODO: Print correct error
			System.out.format(">> ERROR [%d:%d] type mismatch for var := exp\n",6,6);				
		}
		return null;
	}
}
