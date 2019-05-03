package j5analysis2;

public class SrcString implements Letter{
	String input; // input string
	int p;        // index into input of current character
	char c;       // current character

	public SrcString(String input){
		this.input=input; p=0; c=' ';
	}
	public char nextChar() {
		// TODO Auto-generated method stub
		if (p<input.length()) c=input.charAt(p++); else c=EOF;
		return c;
	}
}
