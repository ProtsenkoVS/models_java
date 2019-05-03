package j6lambda;

import java.util.Arrays;
import java.util.stream.IntStream;

public class EvalArray {
	static int evalArrayIt(int[] a){
		int s = 0;
		for(int i=0; i<a.length; i++){
			int c = a[i]+2;
			if(c%3 == 0) s +=c;
		}
		return s;
	}
	static int evalArraySt(int[] a){
		return  IntStream.of(a)             
				         .map(x->x+2)      
				         .filter(x->x%3==0)
				         .sum();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a = {3, 4, 7, 24, 2, 12, 16};
		System.out.println("Eval on array = " + Arrays.toString(a));
		System.out.println("Iter = " + evalArrayIt(a));
		System.out.println("Stream = " + evalArraySt(a));
	}
}
