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
	public void PrintMe() {
		/********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.print("AST NODE STMT ASSIGN NEW EXP\n");

		/***********************************/
		/* RECURSIVELY PRINT VAR + EXP ... */
		/***********************************/
		if (var != null)
			var.PrintMe();
		if (exp != null)
			exp.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "ASSIGN\nleft := right\n");

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (var != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
		if (exp != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
	}

	public TYPE SemantMe() {
		TYPE varType = null;
		TYPE assignmentType = null;

		if (var != null)
			varType = var.SemantMe();
		if (exp != null)
			assignmentType = exp.SemantMe();

		// Check that the new instance is of the same type
		//TYPE assignmentType = newExp.SemantMe();
		if (assignmentType == null) {
			OutputFileWriter.writeError(this.lineNumber, String.format("could not resolve assignment\n"));
		}
		if (varType.isClass()) {
			if (!TYPE_CLASS.isSubClassOf(assignmentType, varType)) {
				OutputFileWriter.writeError(this.lineNumber, "class type mismatch for var := new exp\n");
			}
		} else if (varType.isArray()) {
			TYPE_ARRAY varArrayType = (TYPE_ARRAY) varType;
			if (assignmentType != varArrayType.arrayType) {
				OutputFileWriter.writeError(this.lineNumber, "array type mismatch for var := new exp\n");
			}
		} else {
			OutputFileWriter.writeError(this.lineNumber, "tried to assign a non array/class type with NEW\n");
		}
		
		
		/*if (!varType.equalsOrSubclass(assignmentType)) {
			OutputFileWriter.writeError(this.lineNumber, "type mismatch for var := new exp\n");
		}*/

		return null;
	}
}
