package AST;

public class AST_STMT_ASSIGN_NEWEXP extends AST_STMT {
	public AST_VAR var;
	public AST_NEWEXP exp;

	public AST_STMT_ASSIGN_NEWEXP(AST_VAR var, AST_NEWEXP exp) {
		this.var = var;
		this.exp = exp;
	}
}
