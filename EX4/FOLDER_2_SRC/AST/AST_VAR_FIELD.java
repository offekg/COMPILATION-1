package AST;

import TYPES.*;
import UTILS.Context;

import java.util.ArrayList;
import java.util.List;

import IR.IR;
import IR.IRcommand_Array_Access;
import IR.IRcommand_Field_Access;
import SYMBOL_TABLE.*;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;

public class AST_VAR_FIELD extends AST_VAR {
	public AST_VAR var;
	public String fieldName;
	public String objectStaticClassName;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_FIELD(AST_VAR var, String fieldName) {
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== var -> var DOT ID( %s )\n", fieldName);

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var = var;
		this.fieldName = fieldName;
	}

	/*************************************************/
	/* The printing message for a field var AST node */
	/*************************************************/
	public void PrintMe() {
		/*********************************/
		/* AST NODE TYPE = AST FIELD VAR */
		/*********************************/
		System.out.print("AST NODE FIELD VAR\n");

		/**********************************************/
		/* RECURSIVELY PRINT VAR, then FIELD NAME ... */
		/**********************************************/
		if (var != null)
			var.PrintMe();
		System.out.format("FIELD NAME( %s )\n", fieldName);

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("FIELD\nVAR\n...->%s", fieldName));

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (var != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
	}

	public TYPE SemantMe() {
		TYPE t = null;
		TYPE_CLASS tc = null;

		/******************************/
		/* [1] Recursively semant var */
		/******************************/
		if (var != null)
			t = var.SemantMe();

		/*********************************/
		/* [2] Make sure type is a class */
		/*********************************/
		if (t.isClass() == false) {
			OutputFileWriter.writeError(this.lineNumber, "access field of a non-class variable\n");
		} else {
			tc = (TYPE_CLASS) t;
			this.objectStaticClassName = tc.name;
		}

		/************************************/
		/* [3] Look for fiedlName inside tc, and return its type */
		/************************************/
		TYPE ancestorField = tc.getOverriddenDataMemember(fieldName); 
		if (ancestorField != null) {
			this.type = ((TYPE_CLASS_VAR_DEC)ancestorField).t; 
			this.uniqueId = ((TYPE_CLASS_VAR_DEC)ancestorField).uniqueId;
			return this.type;
		}

		/*********************************************/
		/* [4] fieldName does not exist in class var */
		/*********************************************/
		OutputFileWriter.writeError(this.lineNumber, String.format("field %s does not exist in class\n", fieldName));
		return null;
	}
	
	public TEMP IRme() {
		TEMP objTemp = var.IRme();
		return fieldAccessIR(objTemp, objectStaticClassName, fieldName);
	}
	
	public static TEMP fieldAccessIR(TEMP objTemp, String objectClass, String objectField) {
		TEMP temp = TEMP_FACTORY.getInstance().getFreshTEMP();
		List<String> fieldList = new ArrayList<>(Context.classFields.get(objectClass));
		int fieldNumber = fieldList.indexOf(objectField);
		IR.getInstance().Add_IRcommand(new IRcommand_Field_Access(objTemp, fieldNumber, temp));
		return temp;
	}
}
