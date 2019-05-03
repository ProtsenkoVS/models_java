package j2grammar;

import java.util.*;

public class Grammar {
	// 
	protected Character start;
	protected Set<Character> nonterminals, terminals;
	protected ArrayList<Production> product;
	// for testing	
	public Grammar(char[] left, String[] right){
		product = new ArrayList<> ();
		nonterminals = new TreeSet<>();
		terminals = new TreeSet<>();
		start = null;
		for(int i=0;i<left.length && i<right.length; i++){
			char n = left[i];
			if(i<right.length && Character.isUpperCase(n)){ 
				String rull = right[i];
				if (start==null) start = n;
				nonterminals.add(n);
				for(char c:rull.toCharArray()){
					if(Character.isUpperCase(c)) nonterminals.add(c);
					else terminals.add(c);
				}
				product.add(new Production(n,rull));
			}
		}
		if(start==null) { // початковий нетермінал ЗАВЖДИ є!
			start = 'S'; nonterminals.add('S');
		}
	}
	
	public Character getStart() { return start;	}
	public Set<Character> getNonterminals() {return nonterminals;}
	public Set<Character> getTerminals() {return terminals;	}
	public ArrayList<Production> getProduct() {	return product;}
	
	private Character findLeft(){
		for(Production pr:product){
			if(!pr.getRull().isEmpty() && (char)pr.getNon() == pr.getRull().charAt(0)) return pr.getNon();
		}
		return null;
	}
	public boolean leftRecursion(){ return (findLeft()!=null);}
	
	public void removeLeft(){
		// A->As1 ... A->Asn  ----> N->s1N ... N->snN  N->Eps
		// A->b1  ... A->bm   ----> A->b1N ... A->bmN
		Character ln=null;
		do{	ln=findLeft();
			if(ln!=null){
				char nn = newNonterminal();
				for(int i=0;i<product.size();i++){
					Production pr = product.get(i);
					if(pr.getNon().equals(ln)){
						if((char)pr.getNon() == pr.getRull().charAt(0)){
							pr=new Production(nn,pr.getRull().substring(1)+nn);
						}  else pr=new Production(pr.getNon(), pr.getRull()+nn);
						product.set(i,pr);
					}
				}
				product.add(new Production(nn,""));
			}
		} while (ln!=null);
	}
	
	// перевіряє, що список номерів продукцій задає деякий лівосторонній вивід в граматиці!
	public boolean leftDerivation(ArrayList<Integer> dv){
		boolean r=true;
		Production pr; 
		ArrayDeque<Character> sb = new ArrayDeque<>();
		int i=0;
		sb.push(start);
		while(r && i<dv.size() ){
			r = (dv.get(i)>=0 && dv.get(i) < product.size() && !sb.isEmpty());
			if(r){
				pr=product.get(dv.get(i++));
				r = pr.getNon().equals(sb.peek());
				if(r){
					sb.pop();
					for(int j=pr.getRull().length(); j>0; j--){
						Character c = pr.getRull().charAt(j-1);
						if (Character.isUpperCase(c))sb.push(c);
					}
					//i++;
				}
			}
		}
		return r;
	}
		
	private char newNonterminal(){
		char r = 'A';
		while(nonterminals.contains(r)) r++;
		nonterminals.add(r);
		return r;
	}
	
	public SynTree buildSynTree(ArrayList<Integer> dv){
		SynTree tr = null;
		if(leftDerivation(dv)){
			tr = new SynTree(start);
			buildInDepth(tr,0,dv);
		}
		return tr;
	}
	
	private int buildInDepth(SynTree t, int k, ArrayList<Integer> dv){
		if(Character.isUpperCase(t.getRoot()) && k < dv.size()) {
			String rull = product.get(dv.get(k++)).getRull();
			if(!rull.isEmpty())
				for(int j=0; j<rull.length(); j++){
					SynTree tr1 = new SynTree(rull.charAt(j));
					t.addSon(tr1);
					k = buildInDepth(tr1, k, dv);
				}
			else t.addSon(new SynTree());
		}
		return k;
	}
	
	@Override
	public String toString(){
		String ns = Arrays.toString(nonterminals.toArray());
		String ts = Arrays.toString(terminals.toArray());
		String pl = Arrays.toString(product.toArray());  
		return "[nonterminals -> " + ns + ",\n terminals -> " + ts 
		+ ",\n product -> " + pl + ",\n start - " + start + "\n]";
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello Syntax ....");
		//char[] lS = {'S','S','S','T','T', 'T'};
		//String[] rS ={"S+T","S-T", "T","(S)","d", ""};
		char[] lS = {'S','S','S','T','T','T','T','F','F'};
		String[] rS ={"S+T","S-T", "T", "T*F", "T/F", "T%F", "F", "(S)","d"};
	
		Grammar sExpr = new Grammar(lS,rS);
		System.out.println("....CF grammar sExpr = ");
		System.out.println(sExpr.toString());
		System.out.println(".....Testing left derivation and build ATS ");
		Integer[][] td = { {0,2,4,4}, {0,2,4,4,1}, {3}, {0,9}, {0,1,3}, {0,2},{0,2,5,4}};
		for(int i=0; i<td.length; i++){
			ArrayList<Integer> dr = new ArrayList<>(Arrays.asList(td[i])); 
			System.out.println("derivation = " + dr.toString() + " is = " + sExpr.leftDerivation(dr)); 
			if (sExpr.leftDerivation(dr))
				System.out.println("AST = " + sExpr.buildSynTree(dr));
			
		}
		System.out.println(".....Testing left recursion ");
		System.out.println("Left recursion ? - " + sExpr.leftRecursion());
		sExpr.removeLeft();
		System.out.println("....CF grammar sExpr without left recursion = ");
		System.out.println(sExpr.toString());
	}	
}
