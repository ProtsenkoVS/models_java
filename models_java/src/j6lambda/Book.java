package j6lambda;

import java.util.*;
//import java.util.regex.*;
import java.util.stream.*;

public class Book {
	ArrayList <String>  authors;
	String titleName;
	int pages;
	Set <String> types;
	String pubName;
	
	public Book(ArrayList<String> authors, String titleName, int pages, Set<String> types,  String pubName) {
		this.authors = authors; this.titleName = titleName;
		this.types = types;		this.pages = pages;
		this.pubName = pubName;
	}
	
	public ArrayList<String> getAuthors() { return authors; }
	public String getTitleName() { return titleName; }
	public Set<String> getTypes() { return types; }
	public int getPages() {	return pages;}
	public String getPubName() { return pubName;	}
		
	public String toString() {
		return "Book [authors=" + authors + ", titleName=" + titleName + 
				", pages=" + pages + ", types=" + types	+ 
				", pubName=" + pubName + "]";
	}

  	public static Stream <Book> library() { return Stream.of(libraryStr()).map(Book::buildBook); }
	
	private static String[] libraryStr(){
		String[] informS = 
			{"Buchman S. : Heydemark W. , 1977! , 140,  history: biography , Abatis Publishers ",
			 "Buchman S. , 200 Years of German Humor , 107, history  , Schadenfreude Press ",
			 "Kells C. , Ask Your System Administrator , 1226, computer: dataBase , Core Dump Books ",
			 "Hull H. : Hull K. , But I Did It Unconsciously , 510  , psychology  , Abatis Publishers ",
			 "Heydemark W. , How About Never? , 473,  biography , Abatis Publishers ",
			 "Heydemark W. : Hull K. , I Blame My Mother , 333, biography  , Schadenfreude Press ",
			 "Kellsey , Just Wait Until After School , 86,  children : fantasy , Abatis Publishers ",
			 "Kellsey , Kiss My Boo-Boo , 55,  children , Abatis Publishers ",
			 "Heydemark W. , Not Without My Faberge Egg , 523,  history: biography , Abatis Publishers ",
			 " Kellsey : Hull H. : Hull K. , Perhaps It's a Glandular Problem , 826  , psychology  , Abatis Publishers ",
			 "Heydemark W. , Spontaneous Not Annoying , 507 ,  biography , Abatis Publishers ",
			 "Buchman S. , What Are The Civilian Applications? , 802, history : fantasy  , Schadenfreude Press "
			}; 
		return informS;			
	}	
	private static Book buildBook(String input){
		String[] token = input.split("\\s*,\\s*");
		String[] au = token[0].split("\\s*:\\s*");
		int pg = 0;
		if(token[2].matches("[+-]?\\d+")) pg = Integer.parseInt(token[2]);
		String[] tp =  token[3].split("\\s*:\\s*");
		return new Book(new ArrayList(Arrays.asList(au)), token[1], pg,
                        new TreeSet(Arrays.asList(tp)), token[4] );
	}
	
}

