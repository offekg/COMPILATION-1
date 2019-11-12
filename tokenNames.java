public interface TokenNames {
    public static final int LPAREN = 0;
    public static final int RPAREN = 1;
    public static final int LBRACK = 2;
    public static final int RBRACK = 3;
    public static final int LBRACE = 4;
    public static final int RBRACE = 5;
    public static final int NIL = 6;
    public static final int PLUS = 7;
    public static final int MINUS = 8;
    public static final int TIMES = 9;
    public static final int DIVIDE = 10;
    public static final int COMMA = 11;
    public static final int DOT = 12;
    public static final int SEMICOLON = 13;
    public static final int ELLIPSIS = 14;
    public static final int ASSIGN = 15;
    public static final int EQ = 16;
    public static final int LT = 17;
    public static final int GT = 18;
    public static final int ARRAY = 19;
    public static final int CLASS = 20;
    public static final int EXTENDS = 21;
    public static final int RETURN = 22;
    public static final int WHILE = 23;
    public static final int IF = 24;
    public static final int NEW = 25;
    public static final int INT = 26;
    public static final int STRING = 27;
    public static final int ID = 28;
    public static final int EOF = 29;

    public static String getTokenName(int token_number) {
      switch(token_number) {
        case 0: 
          return "LPAREN";
          break;
        case 1: 
          return "RPAREN";
          break;
        case 2: 
          return "LBRACK";
          break;
        case 3: 
          return "RBRACK";
          break;
        case 4: 
          return "LBRACE";
          break;
        case 5: 
          return "RBRACE";
          break;
        case 6: 
          return "NIL";
          break;
        case 7: 
          return "PLUS";
          break;
        case 8: 
          return "MINUS";
          break;
        case 9: 
          return "TIMES";
          break;
        case 10: 
          return "DIVIDE";
          break;
        case 11: 
          return "COMMA";
          break;
        case 12: 
          return "DOT";
          break;
        case 13: 
          return "SEMICOLON";
          break;
        case 14: 
          return "ELLIPSIS";
          break;
        case 15: 
          return "ASSIGN";
          break;
        case 16: 
          return "EQ";
          break;
        case 17: 
          return "LT";
          break;
        case 18: 
          return "GT";
          break;
        case 19: 
          return "ARRAY";
          break;
        case 20: 
          return "CLASS";
          break;
        case 21: 
          return "EXTENDS";
          break;
        case 22: 
          return "RETURN";
          break;
        case 23: 
          return "WHILE";
          break;
        case 24: 
          return "IF";
          break;
        case 25: 
          return "NEW";
          break;
        case 26: 
          return "INT";
          break;
        case 27: 
          return "STRING";
          break;
        case 28: 
          return "ID";
          break;
      }
    }
}