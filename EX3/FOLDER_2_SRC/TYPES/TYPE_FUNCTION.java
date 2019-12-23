package TYPES;

public class TYPE_FUNCTION extends TYPE {
	/***********************************/
	/* The return type of the function */
	/***********************************/
	public TYPE returnType;

	/*************************/
	/* types of input params */
	/*************************/
	public TYPE_LIST firstParam;
	public TYPE_LIST otherParams;

	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_FUNCTION(TYPE returnType, String name, TYPE_LIST firstParam, TYPE_LIST otherParams) {
		this.name = name;
		this.returnType = returnType;
		this.firstParam = firstParam;
		this.otherParams = otherParams;
	}
}
