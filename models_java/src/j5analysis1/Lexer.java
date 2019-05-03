package j5analysis1;

public class Lexer {
	// лексичний аналіз виразів з цілими числами і проміжками
	//    ............ Вхід : рядок 
	public static final int EOFT = 1;    // represent EOF token type
	public static final int NUMB = 2;
    public static final int LPAREN = 3;  // parenthesis
    public static final int RPAREN = 4;
    public static final int ADDOP = 5;
    public static final int MULOP = 6;
    public static String[] tokenNames =
	        { "n/a", "<EOF>", "NUMB", "LPAREN", "RPAREN", "ADDOP", "MULOP" };
    char c;       // current character
    Letter input; 
   	
	public Lexer(Letter src) { input = src; consume(); }
	
	public String getTokenName(int x) { return tokenNames[x]; }

	public Token nextToken() throws SyntaxError {
	   	while ( c!=input.EOF ) {
	        switch ( c ) {
	                case ' ': case '\t': case '\n': case '\r': whiteSpace(); continue;
	                case '(' : consume(); return new Token(LPAREN, "(");
	                case ')' : consume(); return new Token(RPAREN, ")");
	                case '+' : consume(); return new Token(ADDOP, "+");
	                case '-' : consume(); return new Token(ADDOP, "-");
	                case '*' : consume(); return new Token(MULOP, "*");
	                case '/' : consume(); return new Token(MULOP, "/");
	                case '%' : consume(); return new Token(MULOP, "%");
	                default:
	                    if ( isDigit() ) return number();
	                    throw new SyntaxError("invalid character: "+c);
	        }
	    }
	    return new Token(EOFT,"<EOF>");
	}
	
	void consume(){ c = input.nextChar(); }
	boolean isDigit() { return c>='0'&&c<='9'; }
	Token number() {
		StringBuilder buf = new StringBuilder();
	    do { buf.append(c); consume(); } while ( isDigit() );
	    return new Token(NUMB, buf.toString());
	}
	void whiteSpace() {
		while ( c==' ' || c=='\t' || c=='\n' || c=='\r' ) consume();
	}	 
}
