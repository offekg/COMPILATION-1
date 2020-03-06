package AST;

import TYPES.*;
import IR.*;
import SYMBOL_TABLE.*;
import TEMP.TEMP;

public class AST_PROGRAM extends AST_Node {
	public AST_DEC_LIST decList;

	public AST_PROGRAM(AST_DEC_LIST decList) {
		this.decList = decList;
		SerialNumber = AST_Node_Serial_Number.getFresh();

		if (decList != null)
			System.out.print("====================== Program -> decList      \n");
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe() {
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE PROGRAM\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (decList != null)
			decList.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "PROGRAM " + "\n");

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (decList != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, decList.SerialNumber);
	}

	public TYPE SemantMe() {
		/************************************************************************/
		/* First initialization of symbol table + entering of the global scope. */
		/************************************************************************/
		SYMBOL_TABLE.getInstance().beginScope(ScopeType.GLOBAL_SCOPE);

		/*************************************/
		/* RECURSIVELY PRINT DECLIST ... */
		/*************************************/
		if (decList != null)
			decList.SemantMe();

		SYMBOL_TABLE.getInstance().endScope();

		//OutputFileWriter.writeOk();

		return null;
	}
	
	public TEMP IRme() {
		IR.getInstance().Add_IRcommand(new IRcommand_Label("main"));
		decList.IRmeOnlyClassesAndVariables();
		IR.getInstance().Add_IRcommand(new IRcommand_Jump_Main());
		decList.IRmeWithoutGlobalVariablesAndClasses();;
		return null;
	}
}