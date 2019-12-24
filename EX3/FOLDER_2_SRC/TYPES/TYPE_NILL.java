package TYPES;

public class TYPE_NILL extends TYPE
{
	/**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
	/**************************************/
	private static TYPE_NILL instance = null;

	/*****************************/
	/* PREVENT INSTANTIATION ... */
	/*****************************/
	protected TYPE_NILL() {}

	/******************************/
	/* GET SINGLETON INSTANCE ... */
	/******************************/
	public static TYPE_NILL getInstance()
	{
		if (instance == null)
		{
			instance = new TYPE_NILL();
			instance.name = "nill";
		}
		return instance;
	}
}
