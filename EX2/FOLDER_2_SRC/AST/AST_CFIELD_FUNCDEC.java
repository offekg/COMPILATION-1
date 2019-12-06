package AST;

public abstract class AST_CFIELD_FUNCDEC extends AST_CFIELD {
	public AST_DEC_FUNCDEC funcdec;
	public AST_CFIELD_FUNCDEC(AST_DEC_FUNCDEC funcdec) {
		this.funcdec = funcdec;
		SerialNumber = AST_Node_Serial_Number.getFresh();
	}
}
