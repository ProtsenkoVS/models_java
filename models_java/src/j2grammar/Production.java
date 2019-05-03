package j2grammar;

public class Production {
	private Character non;
	private String rull;

	public Production(Character c, String sr) {
		non=c; rull=sr;
	}

	public Character getNon() {	return non;	}
	
	public String getRull() {return rull; }
	
	public String toString(){
		return non + "->" + (rull.isEmpty()?"Eps":rull);
	}
}