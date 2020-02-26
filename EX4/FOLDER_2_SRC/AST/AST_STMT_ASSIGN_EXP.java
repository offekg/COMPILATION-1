package AST;

import TYPES.*;
import UTILS.Context;
import IR.*;
import SYMBOL_TABLE.*;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;

public class AST_STMT_ASSIGN_EXP extends AST_STMT {
	public AST_VAR var;
	public AST_EXP exp;

	public AST_STMT_ASSIGN_EXP(AST_VAR var, AST_EXP exp) {
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== stmt -> var ASSIGN exp SEMICOLON\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var = var;
		this.exp = exp;
	}

	/*********************************************************/
	/* The printing message for an assign statement AST node */
	/*********************************************************/
	public void PrintMe() {
		/********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.print("AST NODE ASSIGN STMT\n");

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
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "ASSIGN\nvar := exp\n");

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (var != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
		if (exp != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
	}

	public TYPE SemantMe() {
		TYPE var_type = null;
		TYPE exp_type = null;

		if (var != null)
			var_type = var.SemantMe();
		if (exp != null)
			exp_type = exp.SemantMe();

		if (!exp_type.equalsOrSubclass(var_type)) {
			OutputFileWriter.writeError(this.lineNumber, "type mismatch for var := exp\n");
		}
		return null;
	}

	public TEMP IRme() {
		TEMP expTemp = exp.IRme();
		if (var.isVarSimple()) {
			AST_VAR_SIMPLE varSimple = (AST_VAR_SIMPLE) var;
			if (Context.currentClassBuilder != null) {
				if (Context.classFieldList.get(Context.currentClassBuilder).contains(varSimple.name)) {
					TEMP objTemp = Context.currentObject;
					int fieldNumber = Context.classFieldList.get(Context.currentClassBuilder).indexOf(varSimple.name);
					IR.getInstance().Add_IRcommand(new IRcommand_Field_Set(objTemp, fieldNumber, expTemp));
				}
			} else {
				IR.getInstance().Add_IRcommand(new IRcommand_Store(varSimple.name, expTemp));
			}
		} else if (var.isVarField()) {
			AST_VAR_FIELD varField = (AST_VAR_FIELD) var;
			TEMP objTemp = varField.var.IRme();
			int fieldNumber = Context.classFieldList.get(varField.objectStaticClassName).indexOf(varField.fieldName);
			IR.getInstance().Add_IRcommand(new IRcommand_Field_Set(objTemp, fieldNumber, expTemp));
		} else if (var.isVarSubscrip()) {
			AST_VAR_SUBSCRIPT varSubscript = (AST_VAR_SUBSCRIPT) var;
			TEMP arrTemp = varSubscript.var.IRme();
			TEMP subTemp = varSubscript.subscript.IRme();
			IR.getInstance().Add_IRcommand(new IRcommand_Array_Set(arrTemp, subTemp, expTemp));
		}
		return null;
	}
}
