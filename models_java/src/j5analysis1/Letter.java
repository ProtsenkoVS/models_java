package j5analysis1;

public class Letter {
	public static char EOF = (char)-1; // represent end of chars from source
	String input; // source string
	int p;        // index into input of current character
	char c;       // current character

	public Letter(String input){
		this.input=input; p=0; c=' ';
	}
	public char nextChar() {
		if (p<input.length()) c=input.charAt(p++); else c=EOF;
		return c;
	}
}
