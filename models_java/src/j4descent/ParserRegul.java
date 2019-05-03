package j4descent;

public class ParserRegul {
	// регул€рний вираз, що задаЇ мову в алфав≥т≥ {a,b,...,x,y,z}
	// E -> T{'|' T}                   
	// T -> F{F}                    
	// F -> P {'*' | '+' | '?'}           
	// P -> '(' E ')' | 'a' | 'b' | ... | 'x' | 'y' | 'z'
	Letter input;
	char next;	
	public ParserRegul(){ }
	
	public boolean analys(String word){
		input = new Letter(word);
		try{
			next = input.nextChar();
			E(); match('$');
		} catch(SyntaxError ex){
			System.out.println("----Syntax ERROR: " + ex.getMessage());
			return false;
		}
		return true;
	}
	
	void E() throws SyntaxError{
		//System.out.println("E:next=" + next); 
		T(); 
		while(next=='|'){ 
			next=input.nextChar(); T();
		} 
	}	
	void T() throws SyntaxError{
		//System.out.println("T:next=" + next); 
		F();
		while(next=='(' || next>='a' && next<='z') F();
	}	
	void F() throws SyntaxError{
		//System.out.println("F:next=" + next); 
		P(); 
		while (next=='*' || next=='+' || next=='?')	next=input.nextChar();
	}	
	void P() throws SyntaxError{
		//System.out.println("P:next=" + next); 
		if (next=='(' ){
			next=input.nextChar(); E(); match(')'); 
		} 
		else if(next>='a' && next<='z')	next=input.nextChar();
	    else throw new SyntaxError("Expecting one from \"abc...xyz(\", found " + next);	
	}	
	void match(char c) throws SyntaxError{
		if(next==c) next=input.nextChar();
		else throw new SyntaxError("Expecting " + c + ", found " + next);
	}	
	
}
