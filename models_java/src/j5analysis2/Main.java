package j5analysis2;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//	     Letter-interface + SrcFile + SrcString
		//     Parser --> analysFile(file) + analysStr(word)
		String src[] = {"  -9  ",  "  ( 56 -  2) ", " (6 ",  " 9+ (73 -**)", "\t-23 + 45    * \n	( 17 - 5 ) "};
		System.out.println("---------------------------------");
		System.out.println("Syntax expr...  any Source .. ");
		Parser pEx = new Parser();
		for(int i = 0; i<src.length; i++){
			System.out.println(" expr = " + src[i] + " analys = " + pEx.analysStr(src[i]));
		}
		System.out.println("---------------------------------");
		System.out.println(" Analys file test.txt = " + pEx.analysFile("src//test.txt"));
		
		ParserAST pAST = new ParserAST();
		System.out.println("Syntax expr...  AST .... ");
		for(int i = 0; i<src.length; i++){
			AST tr = pAST.analysStr(src[i]);
			System.out.println(" expr = " + src[i] + " analys = " + tr);
			if (tr!=null) System.out.println("........ value expr = " + tr.evalExpr());
		}
	}

}
