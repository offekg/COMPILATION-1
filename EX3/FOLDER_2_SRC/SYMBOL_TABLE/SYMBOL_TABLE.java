/***********/
/* PACKAGE */
/***********/
package SYMBOL_TABLE;

/*******************/
/* GENERAL IMPORTS */
/*******************/

import java.io.PrintWriter;

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TYPES.*;

/****************/
/* SYMBOL TABLE */
/****************/
public class SYMBOL_TABLE {
	private int hashArraySize = 13;

	/**********************************************/
	/* The actual symbol table data structure ... */
	/**********************************************/
	private SYMBOL_TABLE_ENTRY[] table = new SYMBOL_TABLE_ENTRY[hashArraySize];
	private SYMBOL_TABLE_ENTRY top;
	private int top_index = 0;

	/**************************************************************/
	/* A very primitive hash function for exposition purposes ... */
	/**************************************************************/
	private int hash(String s) {
		if (s.charAt(0) == 'l') {
			return 1;
		}
		if (s.charAt(0) == 'm') {
			return 1;
		}
		if (s.charAt(0) == 'r') {
			return 3;
		}
		if (s.charAt(0) == 'i') {
			return 6;
		}
		if (s.charAt(0) == 'd') {
			return 6;
		}
		if (s.charAt(0) == 'k') {
			return 6;
		}
		if (s.charAt(0) == 'f') {
			return 6;
		}
		if (s.charAt(0) == 'S') {
			return 6;
		}
		return 12;
	}

	/****************************************************************************/
	/* Enter a variable, function, class type or array type to the symbol table */
	/****************************************************************************/
	public void enter(String name, TYPE t) {
		System.out.println("~~entering into table: " + name);
		/*************************************************/
		/* [1] Compute the hash value for this new entry */
		/*************************************************/
		int hashValue = hash(name);

		/******************************************************************************/
		/* [2] Extract what will eventually be the next entry in the hashed position */
		/* NOTE: this entry can very well be null, but the behaviour is identical */
		/******************************************************************************/
		SYMBOL_TABLE_ENTRY next = table[hashValue];

		/**************************************************************************/
		/* [3] Prepare a new symbol table entry with name, type, next and prevtop */
		/**************************************************************************/
		SYMBOL_TABLE_ENTRY e = new SYMBOL_TABLE_ENTRY(name, t, hashValue, next, top, top_index++);

		/**********************************************/
		/* [4] Update the top of the symbol table ... */
		/**********************************************/
		top = e;

		/****************************************/
		/* [5] Enter the new entry to the table */
		/****************************************/
		table[hashValue] = e;

		/**************************/
		/* [6] Print Symbol Table */
		/**************************/
		PrintMe();
	}

	/***********************************************/
	/* Find the inner-most scope element with name */
	/***********************************************/
	public TYPE find(String name) {
		SYMBOL_TABLE_ENTRY e;

		for (e = table[hash(name)]; e != null; e = e.next) {
			if (name.equals(e.name)) {
				return e.type;
			}
		}

		return null;
	}
	
	/***********************************************/
	/* Whether an element with this name exists in current scope */
	/***********************************************/
	public boolean isInScope(String name) {
		SYMBOL_TABLE_ENTRY e = top;

		while(e != null && !(e.type instanceof TYPE_FOR_SCOPE_BOUNDARIES)) {
			if (e.name.equals(name)) {
				return true;
			}
			e = e.prevtop;
		}

		return false;
	}
	
	/***********************************************/
	/* Get the type of current scope               */
	/***********************************************/
	public ScopeType getCurrentScopeType() {
		SYMBOL_TABLE_ENTRY e = top;
		TYPE_FOR_SCOPE_BOUNDARIES scope;

		while(e.name != "SCOPE-BOUNDARY") { //  instanceof TYPE_FOR_SCOPE_BOUNDARIES)) {
			System.out.println("in while: " + e.name);
			e = e.prevtop;
		}

		if(e != null && e.name == "SCOPE-BOUNDARY") { //e.type instanceof TYPE_FOR_SCOPE_BOUNDARIES) {
			scope = (TYPE_FOR_SCOPE_BOUNDARIES) e.type;
			return scope.scopeType;
		}
		return ScopeType.ERROR_SCOPE;
	}
	
	/***********************************************/
	/* Get the current function scope type             */
	/***********************************************/
	public TYPE_FOR_SCOPE_BOUNDARIES getFunctionScopeType() {
		
		SYMBOL_TABLE_ENTRY e = top;

		while((e != null) && !(e.type instanceof TYPE_FOR_SCOPE_BOUNDARIES && ((TYPE_FOR_SCOPE_BOUNDARIES) e.type).scopeType == ScopeType.FUNCTION_SCOPE)) {
			e = e.prevtop;
		}
		if(e != null)
			return (TYPE_FOR_SCOPE_BOUNDARIES) e.type;
		return null;
	}
	

	/***************************************************************************/
	/* begine scope = Enter the <SCOPE-BOUNDARY> element to the data structure */
	/***************************************************************************/
	public void beginScope(ScopeType scopeType) {
		/************************************************************************/
		/* Though <SCOPE-BOUNDARY> entries are present inside the symbol table, */
		/* they are not really types. In order to be able to debug print them, */
		/* a special TYPE_FOR_SCOPE_BOUNDARIES was developed for them. This */
		/* class only contain their type name which is the bottom sign: _|_ */
		/************************************************************************/
		enter("SCOPE-BOUNDARY", new TYPE_FOR_SCOPE_BOUNDARIES(scopeType));

		/*********************************************/
		/* Print the symbol table after every change */
		/*********************************************/
		PrintMe();
	}
	
	/***************************************************************************/
	/* begine scope = Enter the <SCOPE-BOUNDARY> element to the data structure */
	/***************************************************************************/
	public void beginScope(ScopeType scopeType, String name,  TYPE returnType) {
		
		
		enter("SCOPE-BOUNDARY", new TYPE_FOR_SCOPE_BOUNDARIES(scopeType, name, returnType));

		/*********************************************/
		/* Print the symbol table after every change */
		/*********************************************/
		PrintMe();
	}

	/********************************************************************************/
	/* end scope = Keep popping elements out of the data structure, */
	/*
	 * from most recent element entered, until a <NEW-SCOPE> element is encountered
	 */
	/********************************************************************************/
	public void endScope() {
		/**************************************************************************/
		/* Pop elements from the symbol table stack until a SCOPE-BOUNDARY is hit */
		/**************************************************************************/
		System.out.println("**Now ending scope and Popping**");
		while (top.name != "SCOPE-BOUNDARY") {
			table[top.index] = top.next;
			top_index = top_index - 1;
			System.out.println("now popping: "+ top.name);
			top = top.prevtop;
		}
		/**************************************/
		/* Pop the SCOPE-BOUNDARY sign itself */
		/**************************************/
		System.out.println("now popping: "+ top.name);
		table[top.index] = top.next;
		top_index = top_index - 1;
		top = top.prevtop;

		/*********************************************/
		/* Print the symbol table after every change */
		/*********************************************/
		PrintMe();
	}

	public static int n = 0;

	public void PrintMe() {
		int i = 0;
		int j = 0;
		String dirname = "./FOLDER_5_OUTPUT/";
		String filename = String.format("SYMBOL_TABLE_%d_IN_GRAPHVIZ_DOT_FORMAT.txt", n++);

		try {
			/*******************************************/
			/* [1] Open Graphviz text file for writing */
			/*******************************************/
			PrintWriter fileWriter = new PrintWriter(dirname + filename);

			/*********************************/
			/* [2] Write Graphviz dot prolog */
			/*********************************/
			fileWriter.print("digraph structs {\n");
			fileWriter.print("rankdir = LR\n");
			fileWriter.print("node [shape=record];\n");

			/*******************************/
			/* [3] Write Hash Table Itself */
			/*******************************/
			fileWriter.print("hashTable [label=\"");
			for (i = 0; i < hashArraySize - 1; i++) {
				fileWriter.format("<f%d>\n%d\n|", i, i);
			}
			fileWriter.format("<f%d>\n%d\n\"];\n", hashArraySize - 1, hashArraySize - 1);

			/****************************************************************************/
			/* [4] Loop over hash table array and print all linked lists per array cell */
			/****************************************************************************/
			for (i = 0; i < hashArraySize; i++) {
				if (table[i] != null) {
					/*****************************************************/
					/* [4a] Print hash table array[i] -> entry(i,0) edge */
					/*****************************************************/
					fileWriter.format("hashTable:f%d -> node_%d_0:f0;\n", i, i);
				}
				j = 0;
				for (SYMBOL_TABLE_ENTRY it = table[i]; it != null; it = it.next) {
					/*******************************/
					/* [4b] Print entry(i,it) node */
					/*******************************/
					fileWriter.format("node_%d_%d ", i, j);
					fileWriter.format("[label=\"<f0>%s|<f1>%s|<f2>prevtop=%d|<f3>next\"];\n", it.name, it.type.name,
							it.prevtop_index);

					if (it.next != null) {
						/***************************************************/
						/* [4c] Print entry(i,it) -> entry(i,it.next) edge */
						/***************************************************/
						fileWriter.format("node_%d_%d -> node_%d_%d [style=invis,weight=10];\n", i, j, i, j + 1);
						fileWriter.format("node_%d_%d:f3 -> node_%d_%d:f0;\n", i, j, i, j + 1);
					}
					j++;
				}
			}
			fileWriter.print("}\n");
			fileWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
	/**************************************/
	private static SYMBOL_TABLE instance = null;

	/*****************************/
	/* PREVENT INSTANTIATION ... */
	/*****************************/
	protected SYMBOL_TABLE() {
	}

	/******************************/
	/* GET SINGLETON INSTANCE ... */
	/******************************/
	public static SYMBOL_TABLE getInstance() {
		if (instance == null) {
			/*******************************/
			/* [0] The instance itself ... */
			/*******************************/
			instance = new SYMBOL_TABLE();

			/*****************************************/
			/* [1] Enter primitive types int, string */
			/*****************************************/
			instance.enter("int", TYPE_INT.getInstance());
			instance.enter("string", TYPE_STRING.getInstance());

			/*************************************/
			/* [2] How should we handle void ??? */
			/*************************************/

			/***************************************/
			/* [3] Enter library function PrintInt */
			/***************************************/
			instance.enter("PrintInt", new TYPE_FUNCTION(TYPE_VOID.getInstance(), "PrintInt",
					new TYPE_LIST(TYPE_INT.getInstance(), null)));
			instance.enter("PrintString", new TYPE_FUNCTION(TYPE_VOID.getInstance(), "PrintString",
					new TYPE_LIST(TYPE_STRING.getInstance(), null)));
			instance.enter("PrintTrace", new TYPE_FUNCTION(TYPE_VOID.getInstance(), "PrintTrace", null));

		}
		return instance;
	}
}
