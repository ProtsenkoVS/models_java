package j3grammarLL1;

import java.util.*;

import j2grammar.*;

public class GrammarLL1 extends Grammar {
	private Map<String,Set<Integer>> terrors;
	private Map<String,Integer> test;
	private Map<Character,Set<Character>> fst, nxt;
	
	public GrammarLL1(char[] left, String[] right) {
		super(left,right);
	}
	// Зазори для доступу до проміжних таблиць, що формуються після виклику isLL1() !!!!!! 
	public Map<String,Set<Integer>> getTerrors(){ return terrors;}
	public Map<String,Integer> getTest(){ return test;}
	public Map<Character,Set<Character>> getFst() { return fst;}
	public Map<Character,Set<Character>> getNxt() { return nxt;}
	
	// analys(input) != null --> повертає вивод dv, якщо input належить мові, що задає граматика
	// analys(input) == null --> якщо input НЕ належить мові, що задає граматика
	// $ - виділений символ -- !terminals.contains('$') !!!!
	public ArrayList<Integer> analys(String input){
		ArrayList<Integer> dv = new ArrayList<> ();
		String wd = input + '$';
		String stack = "" + start + '$';
		while(stack.charAt(0)!='$'){
			//System.out.println("wd = " + wd + " stack = " + stack);
		   	Character r = stack.charAt(0);
		   	stack = stack.substring(1);
		   	if(nonterminals.contains(r)){
		   		String key = ""+r+wd.charAt(0);
				if(test.containsKey(key)){
					Integer j = test.get(key);
					dv.add(j);
					stack = product.get(j).getRull() + stack;
				} else return null;
		   	} else
		   		if(r.equals(wd.charAt(0))) wd = wd.substring(1); else return null;
		}
		if (wd.charAt(0)!='$') return null;
		return dv;
	}
	
	public boolean isLL1(){
		if (leftRecursion())return false;
		buildFst(); buildNxt();
		terrors = new TreeMap<String,Set<Integer>>();
		test = new TreeMap<String,Integer>();
		for(int i=0; i<product.size();i++){
			Production pr = product.get(i);
			Character n = pr.getNon();
			String rull = pr.getRull();
			Set<Character> fs = first(rull); //(""+n);
			Set<Character> ns = follow(n); 
			//System.out.println("..i="+i + " production= " + pr.toString() + " first=" + fs.toString() + " follow=" + ns.toString() );
			for(Character c:fs){
				if(c=='$')	for(Character s:ns) add(n,s,i);
				else add(n,c,i);
			}
		}
		return terrors.isEmpty();
	}
	
	private void add(Character N, Character t, int i){
		String key = ""+N +t;
		if(test.containsKey(key)){
			int pr = test.get(key);
			if (pr!=i){
				addError(key,i);
				if(pr>=0){
					addError(key,pr);
					test.put(key, -1);  // not LL(1) !!!!!!
				}
			}
		} else test.put(key, i);
	
	}
	private void addError(String key, int r){
		Set<Integer> si;
		if(terrors.containsKey(key)) si=terrors.get(key);
		else si = new TreeSet<Integer>();
		si.add(r);
		terrors.put(key, si);
	}
	
	// first + follow : ==>  зроблені public лише для тестування  
	public Set<Character> first(String wd){
		Set<Character> rs = new TreeSet<>();
		if (!wd.isEmpty()){
			Character c = wd.charAt(0);
			if(wd.length()>=1){
				if(fst.get(c).contains('$')){
					addWithoutEps(rs,fst.get(c));
					rs.addAll(first(wd.substring(1)));
				} else rs = fst.get(c);
			}
			else rs = fst.get(c);				
		}
		else rs.add('$');
		return rs;
	}
	
	public Set<Character> follow(Character c){
		Set<Character> rs = new TreeSet<>();
		if(nonterminals.contains(c)) rs = nxt.get(c);
		return rs;
	}	
	
	private void buildFst(){
		Map<Character,Set<Character>> fstp;
		//int st=0;
		// fstp + fst - не повинні посилатися на ОДНУ і ТУ множину (бо вони змінюються !!!!)
		fstp = new TreeMap<>();
		fst  = new TreeMap<>();
		// initial
		for(Character c: terminals){
			fstp.put(c, new TreeSet<>()); 
			TreeSet sc = new TreeSet<>();sc.add(c); 
			fst.put(c, sc);
		}
		for(Character c: nonterminals){
			fstp.put(c, new TreeSet<>()); 
			fst.put(c, new TreeSet<>()); 		
		}
		for(Production pr:product){
			if(pr.getRull().isEmpty()){
				Set ns = fst.get(pr.getNon());
				ns.add('$'); fst.put(pr.getNon(),ns);
			}
		}
		// iteration
		while (!fstp.equals(fst)){
			// deep copy
			fstp = new TreeMap<>();
			for(Character c:fst.keySet()) fstp.put(c, new TreeSet(fst.get(c)));
			                                   //fstp= new TreeMap(fst);
			for(Production pr:product){
				Character n = pr.getNon();
				Set<Character> ns = fst.get(n);
				String rull = pr.getRull();
				Boolean go = true;
				int i=0;
				while (i<rull.length() && go){
					Set<Character> cs = fstp.get((Character)rull.charAt(i));
					go = cs.contains('$');
					addWithoutEps(ns,cs);
					i++;
				}
				if (i==rull.length() && go) ns.add('$');
				fst.put(n,ns);
			}
			//st++;
		}
	}

	private Set<Character> addWithoutEps(Set<Character> bs, Set<Character> as){
		for(Character c:as)                 //******
			if(!c.equals('$')) bs.add(c);   //******
		return bs;
	}
	private void buildNxt(){
		Map<Character,Set<Character>> nxtp;
		//int st=0;
		// nxtp + nxt - не повинні посилатися на ОДНУ і ТУ множину (бо вони змінюються !!!!)
		nxtp = new TreeMap<>();
		nxt  = new TreeMap<>();
		// initial
		for(Character c: nonterminals){
			nxtp.put(c, new TreeSet<>());
			Set<Character> ts = new TreeSet<>();
			if (c.equals(start)) ts.add('$');
			nxt.put(c, ts);
		}
		// iteration
		while (!nxtp.equals(nxt)){
			// deep copy
			nxtp = new TreeMap<>();
			for(Character c:nxt.keySet()) nxtp.put(c, new TreeSet(nxt.get(c)));
			for(Production pr:product){
				Character n = pr.getNon();
				String rull = pr.getRull();
				int i=0;
				while (i<rull.length()){
					//System.out.println("i=" + i + " c= " + rull.charAt(i));
					Character nt = (Character)rull.charAt(i);
					if (nonterminals.contains(nt)){
						Set<Character> cs = nxt.get(nt);			
						if(i<rull.length()-1){
							Set ntc = fst.get((Character)rull.charAt(i+1));
							addWithoutEps(cs,ntc);
							Boolean go = ntc.contains('$');
							int j=i+2;
							while(j<rull.length() && go){
								ntc = fst.get((Character)rull.charAt(j));
								addWithoutEps(cs,ntc);
								go = ntc.contains('$');
								j++;
							}
							if(go)cs.addAll(nxtp.get(n));
						}
						else cs.addAll(nxtp.get(n));
					}
					i++;
				}
			}
		}	
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Testing LL(1)-gramars ....");
		char[] lS1 = {'S','S','S','T','T'};
		String[] rS1 ={"S+T","S-T", "T","(S)","d"};
		char[] lS2 = {'S', 'S', 'S'};
		String[] rS2 = {"aSa", "bSb", ""};
		char[] lS3 = {'S', 'S', 'S'};
		String[] rS3 = {"aSa", "bSb", "c"};	
		char[] lS4 = {'S', 'S', 'S', 'E','E','E','T','T','T','T','F','F'};
		String[] rS4 ={"+E", "-E", "E", "E+T","E-T", "T", "T*F", "T/F", "T%F", "F", "(S)","d"};
		String[] name = {"sExpr", "nPal", "lPal", "fullExpr"};
		GrammarLL1[] gram = {new GrammarLL1(lS1,rS1), new GrammarLL1(lS2,rS2),
					         new GrammarLL1(lS3,rS3), new GrammarLL1(lS4,rS4)};
		for( int i=0; i<gram.length; i++){
			System.out.println("---" + name[i] + "---" );
			System.out.println(gram[i].toString());	
			boolean lfrec = gram[i].leftRecursion();
			System.out.println("Left recurtion ? - " + lfrec);
			if(lfrec){
				gram[i].removeLeft();
				System.out.println("Without lr ==" + gram[i].toString());
			}
			boolean isll1 = gram[i].isLL1();
			System.out.println("isLL1 = " + isll1);
			
			if (!isll1){
				System.out.println("nxt=" + gram[i].getNxt().toString());	
				String testS = gram[i].getTerrors().toString();
				System.out.println("terrors = " + testS);
			} else{
				System.out.println("test=" + gram[i].getTest().toString());
			}
			
		}
	}
}
