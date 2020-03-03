package AST;

import TYPES.*;
import UTILS.Context;

import java.util.ArrayList;
import java.util.List;

import IR.IR;
import IR.IRcommand_Array_Set;
import IR.IRcommand_Field_Set;
import IR.IRcommand_Get_Input_Var;
import IR.IRcommand_StoreLocalVar;
import IR.IRcommand_StoreGlobal;
import SYMBOL_TABLE.*;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;

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
		// TYPE assignmentType = newExp.SemantMe();
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

		/*
		 * if (!varType.equalsOrSubclass(assignmentType)) {
		 * OutputFileWriter.writeError(this.lineNumber,
		 * "type mismatch for var := new exp\n"); }
		 */

		return null;
	}

	public TEMP IRme() {
		TEMP expTemp; // left hand side should be evaluated first.
		if (var.isVarSimple()) {
			AST_VAR_SIMPLE varSimple = (AST_VAR_SIMPLE) var;
			if (Context.currentClassBuilder != null) {
				if (Context.classFields.get(Context.currentClassBuilder).contains(varSimple.name)) {
					TEMP objTemp = TEMP_FACTORY.getInstance().getFreshTEMP();
					IR.getInstance().Add_IRcommand(new IRcommand_Get_Input_Var(objTemp, Context.currentObjectIndex));
					List<String> fieldList = new ArrayList<>(Context.classFields.get(Context.currentClassBuilder));
					int fieldNumber = fieldList.indexOf(varSimple.name);
					expTemp = exp.IRme();
					IR.getInstance().Add_IRcommand(new IRcommand_Field_Set(objTemp, fieldNumber, expTemp));
				}
			} else {
				expTemp = exp.IRme();
				if (!Context.varStack.getLast().contains(varSimple.name) && Context.globals.contains(varSimple.name)) {
					IR.getInstance().Add_IRcommand(new IRcommand_StoreGlobal(varSimple.name, expTemp));
					return expTemp;
				}
				IR.getInstance().Add_IRcommand(new IRcommand_StoreLocalVar(varSimple.name, expTemp));
			}
		} else if (var.isVarField()) {
			AST_VAR_FIELD varField = (AST_VAR_FIELD) var;
			TEMP objTemp = varField.var.IRme();
			expTemp = exp.IRme();
			List<String> fieldList = new ArrayList<>(Context.classFields.get(varField.objectStaticClassName));
			int fieldNumber = fieldList.indexOf(varField.fieldName);
			IR.getInstance().Add_IRcommand(new IRcommand_Field_Set(objTemp, fieldNumber, expTemp));
		} else if (var.isVarSubscrip()) {
			AST_VAR_SUBSCRIPT varSubscript = (AST_VAR_SUBSCRIPT) var;
			TEMP arrTemp = varSubscript.var.IRme();
			TEMP subTemp = varSubscript.subscript.IRme();
			expTemp = exp.IRme();
			IR.getInstance().Add_IRcommand(new IRcommand_Array_Set(arrTemp, subTemp, expTemp));
		}
		return null;
	}
}
