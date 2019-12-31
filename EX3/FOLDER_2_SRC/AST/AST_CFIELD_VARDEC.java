package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_CFIELD_VARDEC extends AST_CFIELD {
	public AST_DEC_VARDEC vardec;

	public AST_CFIELD_VARDEC(AST_DEC_VARDEC vardec) {
		this.vardec = vardec;
		SerialNumber = AST_Node_Serial_Number.getFresh();
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== CFIELD -> VARDEC\n");
	}

	public TYPE_CLASS_VAR_DEC SemantMe() {
		if (!(this.vardec instanceof AST_DEC_VARDEC_OLD)) {
			OutputFileWriter.writeError(this.lineNumber,
					"A declared data member inside a class can be initialized only with a constant value");
		}
		AST_DEC_VARDEC_OLD old_vardec = (AST_DEC_VARDEC_OLD) this.vardec;
		if (old_vardec.exp != null) {
			if (!((old_vardec.exp instanceof AST_EXP_INT) || (old_vardec.exp instanceof AST_EXP_STRING)
					|| (old_vardec.exp instanceof AST_EXP_NIL))) {
				OutputFileWriter.writeError(this.lineNumber,
						"A declared data member inside a class can be initialized only with a constant value");
			}
		}
		TYPE t = vardec.SemantMe();
		return new TYPE_CLASS_VAR_DEC(t, vardec.name);
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe() {
		/**************************************/
		/* AST NODE TYPE = AST CFIELD_VARDEC */
		/**************************************/
		System.out.print("AST NODE CFIELD VAR DEC\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (vardec != null)
			vardec.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "CFIELD\nVAR DEC\n");

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (vardec != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, vardec.SerialNumber);
	}
}
