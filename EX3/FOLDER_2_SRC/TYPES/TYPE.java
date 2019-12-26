package TYPES;

public abstract class TYPE {
	/******************************/
	/* Every type has a name ... */
	/******************************/
	public String name;

	/*************/
	/* isClass() */
	/*************/
	public boolean isClass() {
		return this instanceof TYPE_CLASS;
	}

	/*************/
	/* isArray() */
	/*************/
	public boolean isArray() {
		return this instanceof TYPE_ARRAY;
	}

	public boolean equalsOrSubclass(TYPE type) {
		if (this.isClass() && type.isClass()) {
			return TYPE_CLASS.isSubClassOf((TYPE_CLASS) this, (TYPE_CLASS) type);
		} /*else if((type.isClass() || type.isArray()) && this.equals(TYPE_NILL.getInstance())){
			return true;
		} */else {
			return this.equals(type);
		}
	}
}
