/***********/
/* PACKAGE */
/***********/
package SYMBOL_TABLE;

/*******************/
/* PROJECT IMPORTS */
/*******************/

import TYPES.*;
import UTILS.Context;
import sun.swing.UIAction;

/**********************/
/* SYMBOL TABLE ENTRY */
/**********************/
public class SYMBOL_TABLE_ENTRY {
	/*********/
	/* index */
	/*********/
	int index;

	/********/
	/* name */
	/********/
	public String name;

	/******************/
	/* TYPE value ... */
	/******************/
	public TYPE type;

	/*********************************************/
	/* prevtop and next symbol table entries ... */
	/*********************************************/
	public SYMBOL_TABLE_ENTRY prevtop;
	public SYMBOL_TABLE_ENTRY next;

	/****************************************************/
	/* The prevtop_index is just for debug purposes ... */
	/****************************************************/
	public int prevtop_index;

	private String uniqueId;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public SYMBOL_TABLE_ENTRY(String name, TYPE type, int index, SYMBOL_TABLE_ENTRY next, SYMBOL_TABLE_ENTRY prevtop,
			int prevtop_index) {
		this(name, type, index, next, prevtop, prevtop_index, null);
	}
	
	public SYMBOL_TABLE_ENTRY(String name, TYPE type, int index, SYMBOL_TABLE_ENTRY next, SYMBOL_TABLE_ENTRY prevtop,
			int prevtop_index, String uniqueId) {
		this.index = index;
		this.name = name;
		this.type = type;
		this.next = next;
		this.prevtop = prevtop;
		this.prevtop_index = prevtop_index;
		this.uniqueId = uniqueId == null ? generateUniqueId(name) : uniqueId;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	private static String generateUniqueId(String name) {
		if (Context.nameCounter.containsKey(name)) {
			int count = Context.nameCounter.get(name);
			Context.nameCounter.put(name, ++count);
			return name + count;
		}
		Context.nameCounter.put(name, 1);
		return name;
	}
}
