package AST;

public class AST_STMT_VARDEC extends AST_STMT {
	public AST_DEC_VARDEC vardec;
	public AST_STMT_VARDEC(AST_DEC_VARDEC vardec) {
		this.vardec = vardec;
		SerialNumber = AST_Node_Serial_Number.getFresh();
	}
}
