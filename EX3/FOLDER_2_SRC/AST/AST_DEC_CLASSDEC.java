package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_DEC_CLASSDEC extends AST_DEC {
	public String name;
	public String father;
	public AST_CFIELD_LIST cFieldList;

	public AST_DEC_CLASSDEC(String name, String father, AST_CFIELD_LIST cFieldList) {
		this.name = name;
		this.father = father;
		this.cFieldList = cFieldList;
		SerialNumber = AST_Node_Serial_Number.getFresh();
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== Dec (CLASS) -> CLASS DEC\n");
	}

	public TYPE SemantMe() {
		/*************************/
		/* [1] Begin Class Scope */
		/*************************/
		SYMBOL_TABLE.getInstance().beginScope(ScopeType.CLASS_SCOPE);

		TYPE_CLASS t;
		// Check father is a class
		if (father != null) {
			TYPE fatherType = SYMBOL_TABLE.getInstance().find(father);
			if (!(fatherType instanceof TYPE_CLASS)) {
				OutputFileWriter.writeError(this.lineNumber, "Could not resolve father's class");
			}
			for (AST_CFIELD_LIST field = this.cFieldList; field != null; field = field.tail) {
				if (field.head instanceof AST_CFIELD_FUNCDEC) {
					AST_CFIELD_FUNCDEC currentFunc = (AST_CFIELD_FUNCDEC) field.head;
					TYPE_FUNCTION overidedMethod = ((TYPE_CLASS) fatherType)
							.getOveridedMethod(currentFunc.funcdec.funcName);
					TYPE returnType = SYMBOL_TABLE.getInstance().find(currentFunc.funcdec.returnType);
					if (!returnType.equals(overidedMethod.returnType))
						OutputFileWriter.writeError(this.lineNumber, "Wrong return type for overided method");
					TYPE_LIST overidedParam = overidedMethod.paramTypes;
					TYPE_LIST methodParam = currentFunc.funcdec.params.SemantMe();
					for (TYPE currentType = methodParam.head; methodParam != null; methodParam = methodParam.tail) {
						if (!currentType.equalsOrSubclass(overidedParam.head)) {
							OutputFileWriter.writeError(this.lineNumber, "Error in method params");
						}
						overidedParam = overidedParam.tail;
					}
					if (overidedParam != null) {
						OutputFileWriter.writeError(this.lineNumber, "Error in method params");
					}
				}
			}
			t = new TYPE_CLASS(name, (TYPE_CLASS) fatherType, cFieldList.SemantMe());
		} else {
			t = new TYPE_CLASS(name, null, cFieldList.SemantMe());
		}

		SYMBOL_TABLE.getInstance().endScope();

		/************************************************/
		/* [4] Enter the Class Type to the Symbol Table */
		/************************************************/
		SYMBOL_TABLE.getInstance().enter(name, t);

		/*********************************************************/
		/* [5] Return value is irrelevant for class declarations */
		/*********************************************************/
		return null;
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe() {
		/**************************************/
		/* AST NODE TYPE = AST DEC CLASS DEC */
		/**************************************/
		if (this.father == null)
			System.out.printf("AST NODE CLASS DEC:\n Class %s\n", this.name);
		else
			System.out.printf("AST NODE CLASS DEC:\n Class %s Extends %s\n", this.name, this.father);

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (cFieldList != null)
			cFieldList.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		if (father == null) {
			AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("CLASS DEC\n Class %s\n", this.name));
		} else {
			AST_GRAPHVIZ.getInstance().logNode(SerialNumber,
					String.format("CLASS DEC\n Class %s Extends %s\n", this.name, this.father));
		}

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (cFieldList != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, cFieldList.SerialNumber);
	}
}
