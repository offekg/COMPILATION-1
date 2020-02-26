package AST;

import TYPES.*;
import UTILS.Context;

import java.util.List;

import SYMBOL_TABLE.*;
import TEMP.TEMP;

public class AST_CFIELD_FUNCDEC extends AST_CFIELD {
	public AST_DEC_FUNCDEC funcdec;

	public AST_CFIELD_FUNCDEC(AST_DEC_FUNCDEC funcdec) {
		this.funcdec = funcdec;
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== CFIELD -> FUNCDEC\n");
	}

	public TYPE_CLASS_VAR_DEC SemantMe() {
		TYPE t = funcdec.SemantMe();
		return new TYPE_CLASS_VAR_DEC(t, funcdec.funcName);
	}

	/******************************************************/
	/* The printing message for a CFIELD_FUNCDEC AST node */
	/******************************************************/
	public void PrintMe() {
		/**************************************/
		/* AST NODE TYPE = AST CFIELD_FUNCDEC */
		/**************************************/
		System.out.print("AST NODE CFIELD FUNC DEC\n");

		/*************************************/
		/* RECURSIVELY PRINT SONS OF NODE... */
		/*************************************/
		if (funcdec != null)
			funcdec.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "CFIELD\nFUNC DEC\n");

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (funcdec != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, funcdec.SerialNumber);
	}
	
	public TEMP IRme() {
		String myClass = Context.currentClassBuilder;
		List<String> methodList = Context.classMethodList.get(myClass);
		methodList.add(funcdec.funcName);
		funcdec.IRme();
		return null;
	}
}
