package j4descent;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Recursive descent ....");
		String[] st1= new String[]{"b", "c", "abbaa", "abba", "abbbaab", "abbbaaba"}; 
		ParserG p1 = new ParserG();
		System.out.println("== Grammar G + ParserG ....");
		for(int i=0; i<st1.length;i++)
			System.out.println("... " + st1[i] + " analys: " + p1.analys(st1[i]));
		String[] st2 = new String[]{"0", "-(+1)", "a", "(((7+5)))", "6-6+7-9+(5)", "((9+1)*4+7)%5*6",
                "+-2","+2", "-6*5/3-2"}; 
		ParserExpr p2 = new ParserExpr();
		System.out.println("== Grammar G2 + ParserExpr ....");
		for(int i=0; i<st2.length;i++)
			System.out.println("... " + st2[i] + " analys: " + p2.analys(st2[i]));		
		ParserIter p3 = new ParserIter();
		System.out.println("== Grammar G5 + ParserIter ....");
		for(int i=0; i<st2.length;i++)
			System.out.println("... " + st2[i] + " analys: " + p3.analys(st2[i]));	
		String[] st4= new String[]{"a", "(a)","(x|y)(a|b)", "xb*", "(ab|c)*", "(a?)a", "(ab)?d+", "(ab|c*", "(ab|c*)"}; 
		ParserRegul p4 = new ParserRegul();
		System.out.println("== Grammar G6 + ParserRegul ....");
		for(int i=0; i<st4.length;i++)
			System.out.println("... " + st4[i] + " analys: " + p4.analys(st4[i]));
		}

}
