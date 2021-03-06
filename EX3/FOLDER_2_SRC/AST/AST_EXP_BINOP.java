package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_EXP_BINOP extends AST_EXP {
	AST_BINOP OP;
	public AST_EXP left;
	public AST_EXP right;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_BINOP(AST_EXP left, AST_EXP right, AST_BINOP OP) {
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== exp -> exp BINOP exp\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.left = left;
		this.right = right;
		this.OP = OP;
	}

	/*************************************************/
	/* The printing message for a binop exp AST node */
	/*************************************************/
	public void PrintMe() {
		/*************************************/
		/* AST NODE TYPE = AST SUBSCRIPT VAR */
		/*************************************/
		System.out.print("AST NODE EXP BINOP\n");

		/**************************************/
		/* RECURSIVELY PRINT left + right ... */
		/**************************************/
		if (OP != null)
			OP.PrintMe();
		if (left != null)
			left.PrintMe();
		if (right != null)
			right.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "EXP\nBINOP\n");

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (OP != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, OP.SerialNumber);
		if (left != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, left.SerialNumber);
		if (right != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, right.SerialNumber);
	}

	public TYPE SemantMe() {
		TYPE t1 = null;
		TYPE t2 = null;

		if (left != null)
			t1 = left.SemantMe();
		if (right != null)
			t2 = right.SemantMe();


		if ((t1 == TYPE_INT.getInstance()) && (t2 == TYPE_INT.getInstance())) {
			// add check for zero devision
			if(this.OP.OP == 3){
				AST_EXP_INT mechane = (AST_EXP_INT)this.right ;
				if(mechane.value == 0){
					OutputFileWriter.writeError(this.lineNumber, "Binop error : zero division\n");
				}
			}
			return TYPE_INT.getInstance();
		}

		// add check that if binop is + then also concatination between two strings is
		// allowed
		if (this.OP.OP == 0) {
			if ((t1 == TYPE_STRING.getInstance()) && (t2 == TYPE_STRING.getInstance())) {
				return TYPE_STRING.getInstance();
			}
		}
		
		// equality testing
		if(this.OP.OP == 6) {
			// check types are competible according to instructions :
			if(t1.equalsOrSubclass(t2) || t2.equalsOrSubclass(t1)) {
				return TYPE_INT.getInstance();
			}
		}
		OutputFileWriter.writeError(this.lineNumber, "Binop incompatibale types\n");
		return null;
	}
}
