package j6lambda;

import java.util.*;

public class Statistic {
	// Допоміжний клас  для обчислення і порівняння 
	//    часу виконання окремого виклику методу.   
	//  Використовується для порівняння часу 
	//    формування множини Мандельброта в 
	//    послідовному і паралельному потоках 
	private ArrayList<Stage> all=null;
	public Statistic(){
		all = new ArrayList<>();
	}

	public void beginStage(String nm){
		endStage();
		System.out.println("----------->Stage.." +nm+"..");
		all.add(new Stage(nm));	
	}
	public void endStage(){
		 int s= all.size();
			if(s>0) {
				Stage last = all.get(s-1);
				if (last.end==0) last.endStage();
			} 
	}

	@Override
	public String toString() {
		String r ="Statistic ";
		if(all!=null){
			for(int i=0; i<all.size();i++) r+= "\n" + all.get(i).toString();
		}
		return r;
	}
	private class Stage{
		String name;
		long begin, end, delta;
		Stage(String nm){
			name=nm;
			begin = java.lang.System.currentTimeMillis();
			end = 0;
		}
		public void endStage(){
			end = java.lang.System.currentTimeMillis();
			delta = (end-begin)/ 1000;
		}
		@Override
		public String toString() {
			return "Stage [name=" + name + ", begin=" + begin + ", end=" + end + ", delta(seconds)=" + delta + "]";
		}
		
	}
}
