package AST;

public class AST_DEC_CLASSDEC extends AST_DEC {
	public String name1;
	public String name2;
	public AST_CFIELD_LIST cFieldList;
	public AST_DEC_CLASSDEC(String name1, String name2, AST_CFIELD_LIST cFieldList) {
		this.name1 = name1;
		this.name2 = name2;
		this.cFieldList = cFieldList;
		SerialNumber = AST_Node_Serial_Number.getFresh();
	}
}
