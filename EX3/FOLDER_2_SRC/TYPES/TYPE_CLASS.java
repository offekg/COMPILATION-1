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
	public TYPE_LIST data_members;

	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_CLASS(String name, TYPE_CLASS father, TYPE_LIST data_members) {
		this.name = name;
		this.father = father;
		this.data_members = data_members;
	}

	public TYPE_FUNCTION getOveridedMethod(String name) {
		for (TYPE_LIST dataMember = this.data_members; dataMember != null; dataMember = dataMember.tail) {
			if (dataMember.head instanceof TYPE_FUNCTION) {
				if (((TYPE_FUNCTION) dataMember.head).name == name)
					return (TYPE_FUNCTION) dataMember.head;
			}
		}
		if (this.father != null)
			return this.father.getOveridedMethod(name);
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
}
