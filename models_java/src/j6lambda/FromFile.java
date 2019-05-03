package j6lambda;

import java.nio.file.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.*;

public class FromFile {
	
	static Long cntWords(String file){
		Pattern delim = Pattern.compile("\\s+");
		try (Stream<String> lines = Files.lines(Paths.get(file))) {
			Stream<String> words = lines.flatMap(l->delim.splitAsStream(l));
			return words.collect(Collectors.counting());
		} catch(Exception ex){
	     	System.out.println("cntWords: " + ex.getMessage());
	     	return null;
	    }            	
	}
	
	static Long cntWordsWitha(String file){
		Pattern delim = Pattern.compile("\\s+");
		try (Stream<String> lines = Files.lines(Paths.get(file))) {
			Stream<String> words = lines.flatMap(l->delim.splitAsStream(l));
			return words.filter(Pattern.compile("\\.*a\\.*")
					    .asPredicate())
					    .collect(Collectors.counting());
		} catch(Exception ex){
	     	System.out.println("cntWordsWitha: " + ex.getMessage());
	     	return null;
	    }            	
	}
	
	static Long cntDistinctWords(String file){
		Pattern delim = Pattern.compile("\\s+");
		try (Stream<String> lines = Files.lines(Paths.get(file))) {
			Stream<String> words = lines.flatMap(l->delim.splitAsStream(l));
			return words.distinct()
					    .count();
		} catch(Exception ex){
	     	System.out.println("cntDistinctWords: " + ex.getMessage());
	     	return null;
	    }            	
	}
	
	static Map<String,Long> frequentWords(String file){
		Pattern delim = Pattern.compile("\\s+");
		try (Stream<String> lines = Files.lines(Paths.get(file))) {
			Stream<String> words = lines.flatMap(l->delim.splitAsStream(l));
			return words.collect(Collectors.groupingBy(x->x,Collectors.counting()));
		} catch(Exception ex){
	     	System.out.println("frequentWords: " + ex.getMessage());
	     	return null;
	    }            	
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String nfile = "src\\simple_file.txt";
		System.out.println("........ Work with file: " + nfile);
		System.out.println(" Counting words = " + cntWords(nfile));
		System.out.println(" Counting words with a = " + cntWordsWitha(nfile));
		System.out.println(" Counting distinct words = " + cntDistinctWords(nfile));
		Map<String,Long> m = frequentWords(nfile);
		System.out.println(" Frequent words \n  " + m);
	}	
}
