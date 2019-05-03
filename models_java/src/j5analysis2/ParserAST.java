package j5analysis2;

public class ParserAST {
	// синтаксичний аналіз виразів з цілими числами і проміжками
	//    .... Вхід: рядок (analysStr) або файл (analysFile) і вихід AST 
	//  expr   ::= [AddOp] term { AddOp term}
	//  term   ::= factor { MulOp factor}
	//  factor ::= '(' expr ')' | Numb
	//  AddOp  ::= '+' | '-'
	//  MulOp  ::= '*' | '/' | '%'
	//  Numb   ::= digit{digit} --- digit=0..9
	Lexer input; 
	Token next;   
    public ParserAST() { }
    public AST analysFile(String file){
  		input = new Lexer(new SrcFile(file));
		return synAnalys();
     }
    public AST analysStr(String src){
 		input = new Lexer(new SrcString(src));
		return synAnalys();
	}    
     
    AST synAnalys(){
    	AST t;
 	 	try{
			next = input.nextToken();
			t=expr(); match(Lexer.EOFT);
		} catch(SyntaxError ex){
			System.out.println("----Syntax ERROR: " + ex.getMessage());
			return null;
		}
		return t;	
    }
    
    AST expr() throws SyntaxError{
  		AST t = null,t1;
   		if(next.type==Lexer.ADDOP) {
  			if (next.text.equals("-")) t= new AST(next); 
  			next=input.nextToken();
  		}
  		if (t==null) t=term(); else t.addSon(term());
  		while (next.type==Lexer.ADDOP){
  			t1 = new AST(next);	next=input.nextToken();
  			t1.addSon(t); t1.addSon(term()); t=t1;
  		}
  		return t;
  	}	
	AST term() throws SyntaxError{
		AST t,t1;
		t = factor(); 
		while (next.type==Lexer.MULOP){
			t1 = new AST(next);	next=input.nextToken();
			t1.addSon(t); t1.addSon(factor()); 	t=t1;
		}
		return t;
	}	 
	AST factor() throws SyntaxError{
		AST t=null;
		if(next.type == Lexer.LPAREN){
			next=input.nextToken(); t=expr(); match(Lexer.RPAREN); 
		} 
		else if (next.type == Lexer.NUMB) {
			t = new AST(next); next=input.nextToken(); 
		}
		else 
			throw new SyntaxError("Expecting LPAREN or NUMB, found " + input.getTokenName(next.type));
		return t;
	}      
	void match(int x) throws SyntaxError {
		if ( next.type == x ) next=input.nextToken(); 
		else throw new SyntaxError("expecting "+input.getTokenName(x)+ "; found "+ next);
	}
}
