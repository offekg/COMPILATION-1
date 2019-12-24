package TYPES;

public class TYPE_FOR_SCOPE_BOUNDARIES extends TYPE
{
	/****************/
	/* CTROR(S) ... */
	/****************/
	public ScopeType scopeType;
	public TYPE returnType;
	public String name;
	
	public TYPE_FOR_SCOPE_BOUNDARIES(ScopeType type)
	{
		this.scopeType = type;
	}
	
	public TYPE_FOR_SCOPE_BOUNDARIES(ScopeType type, String name)
	{
		this.scopeType = type;
		this.name = name;
	}
	
	public TYPE_FOR_SCOPE_BOUNDARIES(ScopeType type, String name, TYPE returnType)
	{
		this.scopeType = type;
		this.name = name;
		this.returnType = returnType;
	}
}
