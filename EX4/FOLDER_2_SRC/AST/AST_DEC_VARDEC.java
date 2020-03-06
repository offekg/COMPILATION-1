package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public abstract class AST_DEC_VARDEC extends AST_DEC {
	public String name;
	public String type;
	public boolean isGlobal = false;
	public String uniqueId;
}
