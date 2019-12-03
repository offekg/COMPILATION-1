package AST;

public class AST_DEC_FUNCDEC extends AST_DEC {
	public String name1;
	public String name2;
	public AST_TWO_ID_LIST twoIdList;
	public AST_STMT_LIST stmtList;
	public AST_DEC_FUNCDEC(String name1, String name2, AST_TWO_ID_LIST twoIdList, AST_STMT_LIST stmtList) {
		this.name1 = name1;
		this.name2 = name2;	
		this.twoIdList = twoIdList;
		this.stmtList = stmtList;
	}
}
