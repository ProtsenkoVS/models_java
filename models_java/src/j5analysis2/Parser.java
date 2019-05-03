package j5analysis2;


public class Parser {
	// синтаксичний аналіз виразів з цілими числами і проміжками
	//    ............ Вхід: рядок (analysStr) або файл (analysFile)
	//  expr   ::= [AddOp] term { AddOp term}
	//  term   ::= factor { MulOp factor}
	//  factor ::= '(' expr ')' | Numb
	//  AddOp  ::= '+' | '-'
	//  MulOp  ::= '*' | '/' | '%'
	//  Numb   ::= digit{digit} --- digit=0..9
	Lexer input; 
	Token next;   
    public Parser() { }
   
    public boolean analysFile(String file){
  		input = new Lexer(new SrcFile(file));
		return synAnalys();
     }
    
    public boolean analysStr(String word){
 		input = new Lexer(new SrcString(word));
		return synAnalys();
	}    
          
    boolean synAnalys(){
    	try{
    		next = input.nextToken();
			expr();
			match(Lexer.EOFT);
		} catch(SyntaxError ex){
			System.out.println("----Syntax ERROR: " + ex.getMessage());
			return false;
		}
		return true;	
    }
    
    void expr() throws SyntaxError{
  		if(next.type==Lexer.ADDOP) next=input.nextToken(); 
		term();
		while (next.type==Lexer.ADDOP){
			next=input.nextToken();  term();
		}
	}	
	void term() throws SyntaxError{
		factor(); 
		while (next.type==Lexer.MULOP){
			next=input.nextToken();  factor();
		}
	}	
	void factor() throws SyntaxError{
		if(next.type == Lexer.LPAREN){
			next=input.nextToken(); expr(); match(Lexer.RPAREN); 
		} 
		else if (next.type == Lexer.NUMB) next=input.nextToken(); 
		else 
			throw new SyntaxError("Expecting LPAREN or NUMB, found " + input.getTokenName(next.type));
	}   
	
	public void match(int x) throws SyntaxError {
		if ( next.type == x ) next=input.nextToken(); 
		else throw new SyntaxError("expecting "+input.getTokenName(x)+ "; found "+ next);
	}
}
