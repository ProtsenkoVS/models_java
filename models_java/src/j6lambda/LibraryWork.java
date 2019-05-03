package j6lambda;

import java.util.*;
import java.util.Map.*;
import java.util.stream.*;

public class LibraryWork {
	// загальна кількість книг в бібліотеці
	public long countAllBooks(Stream <Book> library){
		return library.collect(Collectors.counting());
		//return library.count();
	}
	// множина всіх авторів, чиї книги є в бібліотеці
	public Set<String> allAuthors(Stream <Book> library){
		//Stream<String> sa = library.flatMap(x->x.getAuthors().stream());
		return library.flatMap(x->x.getAuthors().stream())   
				      .collect(Collectors.toSet());
	}
	// кількість книг випущених кожним видавництвом (ітератор)
	public Map <String, Integer> numberOfBooksC(Stream <Book> library){
		Map<String, List<Book>> booksByPublishes =
				library.collect(Collectors.groupingBy(book->book.getPubName()));  // (Book::getPubName) (book->book.getPubName())
		Map <String, Integer> numberOfBooks = new HashMap<>();
		for(Entry <String, List<Book>> entry : booksByPublishes.entrySet()){
			numberOfBooks.put(entry.getKey(), entry.getValue().size());
		}
		return numberOfBooks;
	}
	// кількість книг випущених кожним видавництвом (понижуючий колектор)
	public Map <String, Long> numberOfBooksS(Stream <Book> library){
		return library.collect(Collectors.groupingBy(Book::getPubName, // (Book::getPubName) book->book.getPubName()
				                      Collectors.counting()));
	}
	// список книг випущених кожним видавництвом  (ітератор)
	public Map <String, List<String>> nameOfBooksC(Stream <Book> library){
		Map<String, List<Book>> booksByPublishes =
				library.collect(Collectors.groupingBy(book->book.getPubName()));
		Map <String, List<String>> nameOfBooks = new HashMap<>();
		for(Entry <String, List<Book>> entry : booksByPublishes.entrySet()){
			nameOfBooks.put(entry.getKey(), entry.getValue()
					                             .stream()
					                             .map(b->b.getTitleName())  //(Book::getTitleName)
					                             .collect(Collectors.toList()));
		}
		return nameOfBooks;
	}
	// список книг випущених кожним видавництвом  (понижуючий колектор)
	public Map <String, List<String>> nameOfBooksS(Stream <Book> library){
		return library.collect(Collectors.groupingBy(book->book.getPubName(), // Book::getPubName
                               Collectors.mapping(b->b.getTitleName(),        //Book::getTitleName,
                               Collectors.toList())));
	}	
	
 	public static void main(String[] args) {
		LibraryWork sw = new LibraryWork();
		System.out.println("......Work with library (class Book)...... ");
		System.out.println(" All books in library: ");
		Book.library().forEach(System.out::println);
		System.out.println(" Size library = " + sw.countAllBooks(Book.library()));
		System.out.println(" All authors = " + sw.allAuthors(Book.library()));
		Map <String, Integer> cntBooksC = sw.numberOfBooksC(Book.library());
		System.out.println(" Counts books in publishers (iterator): " + cntBooksC.toString());
		Map <String, Long> cntBooksS = sw.numberOfBooksS(Book.library());
		System.out.println(" Counts books in publishers (collector): " + cntBooksS.toString());
		Map <String, List<String>> nmBooksC = sw.nameOfBooksC(Book.library());
		System.out.println(" Names books in publishers (iterator): " + nmBooksC.toString());
		Map <String, List<String>> nmBooksS = sw.nameOfBooksS(Book.library());
		System.out.println(" Names books in publishers (collector): " + nmBooksS.toString());
	}
}
