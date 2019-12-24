package TYPES;

public abstract class TYPE
{
	/******************************/
	/*  Every type has a name ... */
	/******************************/
	public String name;

	/*************/
	/* isClass() */
	/*************/
	public boolean isClass(){ return false;}

	/*************/
	/* isArray() */
	/*************/
	public boolean isArray(){ return false;}

	public boolean equalsOrSubclass(TYPE type) {
		if (this instanceof TYPE_CLASS && type instanceof TYPE_CLASS) {
			return TYPE_CLASS.isSubClassOf((TYPE_CLASS)this, (TYPE_CLASS)type);
		} else {
			return this.equals(type);
		}
	}
}
