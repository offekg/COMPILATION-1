package AST;

import TYPES.*;
import UTILS.Context;

import java.util.ArrayList;
import java.util.List;

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
		TEMP expTemp; // left hand side should be evaluated first.
		if (var.isVarSimple()) {
			AST_VAR_SIMPLE varSimple = (AST_VAR_SIMPLE) var;
			
			if (Context.currentClassBuilder != null
					&& Context.classFields.get(Context.currentClassBuilder).contains(varSimple.name)) {
				if (Context.varStack.getLast().contains(varSimple.name)) {
					expTemp = exp.IRme();
					IR.getInstance().Add_IRcommand(new IRcommand_StoreLocalVar(varSimple.name, expTemp));
					return null;
				}
				TEMP objTemp = TEMP_FACTORY.getInstance().getFreshTEMP();
				IR.getInstance().Add_IRcommand(new IRcommand_Get_Input_Var(objTemp, Context.currentObjectIndex));
				List<String> fieldList = new ArrayList<>(Context.classFields.get(Context.currentClassBuilder));
				int fieldNumber = fieldList.indexOf(varSimple.name);
				expTemp = exp.IRme();
				IR.getInstance().Add_IRcommand(new IRcommand_Field_Set(objTemp, fieldNumber, expTemp));
			} else {
				expTemp = exp.IRme();
				if ((!Context.varStack.getLast().contains(varSimple.name)
						|| Context.varStack.getLast().equals(Context.globals))
						&& Context.globals.contains(varSimple.name)) {
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
