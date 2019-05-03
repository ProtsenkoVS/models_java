package j4descent;

public class ParserExpr {
	// повний арифметичний вираз з цифр (0..9), дужок () і операцій +-*/%
	// S -> +E | -E | E                      ...  1-3
	// E -> TA   A -> +TA | -TA | Eps        .... 4-7
	// T -> FB   B -> *FB | /FB | %FB | Eps  .... 8-12
	// F -> (S) | 0 | .. | 9                 .... 13-23
	Letter input;
	char next;	
	public ParserExpr(){ }
	
	public boolean analys(String word){
		input = new Letter(word);
		try{
			next = input.nextChar();
			S(); match('$');
		} catch(SyntaxError ex){
			System.out.println("----Syntax ERROR: " + ex.getMessage());
			return false;
		}
		return true;
	}
	void S() throws SyntaxError{
		if(next=='+'){
			next=input.nextChar(); E();
		} else if(next=='-'){
			next=input.nextChar(); E();
		}
		else E();
	}	
	void E() throws SyntaxError{
		T(); A(); 
	}	
	void A() throws SyntaxError{
		if(next=='+'){
			next=input.nextChar(); T(); A();
		} else if(next=='-'){
			next=input.nextChar(); T(); A();
		}
	}
	void T() throws SyntaxError{
		F(); B();
	}	
	void B() throws SyntaxError{
		if(next=='*'){
			next=input.nextChar(); F(); B(); 
		} else if(next=='/'){
			next=input.nextChar(); F(); B(); 
		}
		else if(next=='%'){
			next=input.nextChar(); F(); B(); 
		}
	}	
	void F() throws SyntaxError{
		if (next=='(' ){
			next=input.nextChar(); S(); match(')'); 
		} 
		else if(next<='9' && next>='0')	next=input.nextChar();
	    else throw new SyntaxError("Expecting one from \"0123456789(\", found " + next);
	}
	void match(char c) throws SyntaxError{
		if(next==c) next=input.nextChar();
		else throw new SyntaxError("Expecting " + c + ", found " + next);
	}
}
