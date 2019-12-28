package TYPES;

public class TYPE_CLASS extends TYPE {
	/*********************************************************************/
	/* If this class does not extend a father class this should be null */
	/*********************************************************************/
	public TYPE_CLASS father;

	/**************************************************/
	/* Gather up all data members in one place */
	/* Note that data members coming from the AST are */
	/* packed together with the class methods */
	/**************************************************/
	public TYPE_CLASS_VAR_DEC_LIST data_members;

	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_CLASS(String name, TYPE_CLASS father, TYPE_CLASS_VAR_DEC_LIST data_members) {
		this.name = name;
		this.father = father;
		this.data_members = data_members;
	}

	public TYPE_FUNCTION getOverriddenMethod(String name) {
		TYPE_CLASS_VAR_DEC overriddenDataMember = this.getOverriddenDataMemember(name);
		if (overriddenDataMember != null && overriddenDataMember.t instanceof TYPE_FUNCTION) {
			return (TYPE_FUNCTION) overriddenDataMember.t;
		}
		return null;
	}

	public TYPE_CLASS_VAR_DEC getOverriddenDataMemember(String name) {
		for (TYPE_CLASS_VAR_DEC_LIST dataMember = this.data_members; dataMember != null; dataMember = dataMember.tail) {
			if (dataMember.head.name == name) {
				return dataMember.head;
			}
		}
		if (this.father != null)
			return this.father.getOverriddenDataMemember(name);
		return null;
	}

	// Returns whether typeClass is a subclass of typeAncestor.
	public static boolean isSubClassOf(TYPE typeClass, TYPE typeAncestor) {
		if (!(typeClass instanceof TYPE_CLASS && typeAncestor instanceof TYPE_CLASS)) {
			return false;
		}
		TYPE_CLASS current = (TYPE_CLASS) typeClass;
		while (current != null) {
			if (current.equals(typeAncestor)) {
				return true;
			}
			current = current.father;
		}
		return false;
	}
	
	public void addDataMember(TYPE_CLASS_VAR_DEC dataMember) {
		if (this.data_members == null) {
			this.data_members = new TYPE_CLASS_VAR_DEC_LIST(dataMember, null);
		} else {
			TYPE_CLASS_VAR_DEC_LIST current = this.data_members;
			TYPE_CLASS_VAR_DEC_LIST next = current.tail;
			while (next != null) {
				current = next;
				next = current.tail;
			}
			next = new TYPE_CLASS_VAR_DEC_LIST(dataMember, null);
			current.tail = next;
		}
	}
}
