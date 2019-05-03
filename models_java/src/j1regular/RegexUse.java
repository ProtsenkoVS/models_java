 package j1regular;

import java.util.Arrays;
import java.util.regex.*;

public class RegexUse {
	// в рядку шукаються УСІ поштові адреси email=name@domens
	public void findEmail(String wem){
		String rem = "\\w+@(\\w+\\.)+[a-z]{2,4}";  
		Pattern pat = Pattern.compile(rem);
		Matcher mch = pat.matcher(wem);
		while (mch.find()) System.out.println("e-mail: " + mch.group() );
	}

	// s - ціле зі знаком => value, інакше => null
	private Integer parseInt(String s){
		Integer r = null;
		String reg = "\\s*([+-])?(\\d+)\\s*";
		Pattern ip = Pattern.compile(reg);
		Matcher im = ip.matcher(s);
		if (im.matches()){                                             
			r = toInt(im.group(2));
			String sign = im.group(1); 
			if((sign!=null) && (sign.charAt(0)=='-')) r *= (-1);
		}
		return r;
	}
	private int toInt(String s){
		int r =0;
		for(int i=0; i<s.length(); i++)	r=r*10+(s.charAt(i)- '0');
		return r;
	}
	
	// s - рядок - цілі числа, що вівіляються комою, можливо, з проміжками 
	// .. результат - якщо вірно, то-сума ВСІХ чисел, в іншому випадку-null
	public Integer listInt(String s){
		Integer t=0, r = 0;
		String[] sInt = s.split(",");
		int i = 0;
		do{
			t = parseInt(sInt[i++]);
			if(t==null) r=null; else r +=t;
		} while ((r!=null)&& (i<sInt.length));
		return r;
	}

	public Double parseDouble(String s){
		Double r = null;
		String reg = "(?<i>[+-]?\\d+)(?<f>\\.\\d+)?(?<p>[Ee][+-]?\\d+)?";
		Pattern dp = Pattern.compile(reg);
		Matcher dm = dp.matcher(s);
		if (dm.matches() && !s.matches("[+-]?\\d+")){ 
			String i = dm.group("i"); // ціла частина
			String f = dm.group("f"); // можливо дробова частина 
			String p = dm.group("p"); // можлива степінь 
			double rd = 0;
			// обраховуємо цілу частину і перетворюючи в double
			String si=i;
			if(i.charAt(0)=='+' || i.charAt(0)=='-') si=i.substring(1);
			for(int t=0; t<si.length(); t++) rd=rd*10+(si.charAt(t)- '0');
			if(i.charAt(0)=='-') rd = -rd;
			if (f!=null) {    // додаємо дробову частину
				double coef = 1;
				for(int j=1; j<f.length(); j++){
					coef /= 10;
					rd += (f.charAt(j)-'0') * coef;
				}
			}
			if (p!=null){	  //  додаємо степінь
				String pi = p.substring(1);
				if(p.charAt(1)=='+' || p.charAt(1)=='-') pi=p.substring(2);
				double pow = 1;
				int pw = 0;
				for(int k=0; k<pi.length(); k++) pw = pw*10+(pi.charAt(k)-'0');
				for(int k=0; k<pw; k++) pow = pow*10;
				if(p.charAt(1)=='-') rd /= pow; else rd *= pow;
			}
			r = rd;
		}
		return r;
	}	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("...........Regular expressions....");
	
		String re1 = "a+y";
		String[] sa = {"aay", "caayyacaayc"};
		System.out.println(".......Use methods of String and regular expression: " + re1); 
		for(int i=0; i<sa.length; i++){
			System.out.println("...Рядок.. " + sa[i] + ":"); 
			System.out.println("Відповідає виразу " + re1 + ": " + sa[i].matches(re1));
			System.out.println("Розбиваємо за виразом " + re1 + ": " + Arrays.toString(sa[i].split(re1)));
			System.out.println("Заміняємо перше входження виразу " + re1 + " на W : " + sa[i].replaceFirst(re1,"W"));
			System.out.println("Заміняємо всі входження виразу " + re1 + " на W : " + sa[i].replaceAll(re1,"W"));
		}
		
		RegexUse ru = new RegexUse();
		
		String se = "Adress for works: procukvs@gmail.com and my@distedu.ukma.edu.ua ";
		System.out.println(".......Find all Email in word: " + se);
		ru.findEmail(se);
		
		String[] si = {"567","-7","+-67","    76  ","-2  "," +43  " };
		System.out.println(".......Testing parseInt ...");
		for(int i=0; i<si.length; i++)
			System.out.println("s[" + i+"]="+si[i] + " result = " + ru.parseInt(si[i])); 
		
		String[] ss = {" -567 , +90   , -60 ", "-7 +-67", " 45 ", "", "  76  ,-2  "," +43  " };		
		System.out.println("----------------\nШукаємо суму ВСІХ цілих чисел в непорожньому списку");
		//for(int i=0; i<ss.length; i++)	System.out.println("\"" + ss[i] + "\" = " + ru.listInt(ss[i]));
		for(String s:ss) System.out.println("\"" + s + "\" = " + ru.listInt(s));
	
		String[] sd = {"0.567","-0.7","+-67","76.01e+2","1.04E2", "-2e-2","+43", "0.045E-7", "-5e10" };	
		System.out.println("----------------\nРозпізнаємо дійсні числа");
		for(int i=0; i<sd.length; i++){
//			System.out.println("\"" + sd[i] + "\" = " + ru.onlyDouble(sd[i]));
			System.out.println("\"" + sd[i] + "\" - " + ru.parseDouble(sd[i]));
		}	
	}
	
}
