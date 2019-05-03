package j5analysis1;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String src[] = {"  -9  ",  "  ( 56 -  2) ", " (6 ",  " 9+ (73 -**)", "\t-23 + 45    * \n	( 17 - 5 ) "};
		Parser p1 = new Parser();
		System.out.println("Syntax expr... ");
		for(int i = 0; i<src.length; i++){
			System.out.println(" expr = " + src[i] + " analys = " + p1.synAnalys(src[i]));
		}
	}

}
