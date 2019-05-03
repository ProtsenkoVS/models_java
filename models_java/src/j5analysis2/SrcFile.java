package j5analysis2;

import java.io.*;

public class SrcFile implements Letter {
	BufferedReader r;
	String input; // input string
	int p;        // index into input of current character
	char c;       // current character
	
	public SrcFile(String name) {
		try {
			r = new BufferedReader( new FileReader(name));
			inputLine();  consume();
		} catch(Exception e) {
			System.out.println(">>>: SrcFile open file " + name + ":  " + e.getMessage());	
			input = null; c=EOF;
		}
    }
	public char nextChar() {
		consume(); 
		return c;
	}
	
	private void inputLine(){
	  	if (r!=null) {
	  		try {
	  			input = r.readLine(); p=0; c=' ';
	  			System.out.println(">>>:" + input + "..");  
	  			if (input==null) c=EOF;
	   		} catch(Exception e) {
	  			System.out.println(">>>: SrcFile: inputLine " + e.getMessage());	
	   		}	
	  	} else c=EOF;
	}
    private void consume() {
    	if (input != null){
    		if ( !(p < input.length()) ) inputLine(); 
    	} else c= EOF;	
        if (c!=EOF) c = input.charAt(p++);
    }	
}
