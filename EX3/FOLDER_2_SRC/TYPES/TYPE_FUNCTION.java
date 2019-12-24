package TYPES;

public class TYPE_FUNCTION extends TYPE {
	/***********************************/
	/* The return type of the function */
	/***********************************/
	public TYPE returnType;

	/*************************/
	/* types of input params */
	/*************************/
	public TYPE_LIST paramTypes;

	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_FUNCTION(TYPE returnType, String name, TYPE_LIST paramTypes) {
		this.name = name;
		this.returnType = returnType;
		this.paramTypes = paramTypes;
	}
}
