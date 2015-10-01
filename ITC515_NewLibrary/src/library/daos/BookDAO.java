package library.daos;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.IBookHelper;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;

public class BookDAO implements IBookDAO {

	IBookHelper helper;
	int nextId;
	Map<Integer, IBook> bookMap;
	
	
	public BookDAO (IBookHelper helper) {
		if (helper == null) {
			throw new IllegalArgumentException("helper cannot be null");
		} else {
			this.helper = helper;
			this.bookMap = new HashMap<Integer, IBook>();
			this.nextId = 1;
		}
		
	}
	
	@Override
	public IBook addBook(String author, String title, String callNo) {
		// TODO Auto-generated method stub
		int id = getNextId();
		IBook newBook = helper.makeBook(author, title, callNo, id);
		bookMap.put(Integer.valueOf(id), newBook);
		
		
		return newBook;
	}

	@Override
	public IBook getBookByID(int id) {
		// TODO Auto-generated method stub
		IBook book = bookMap.get(id);
		if (book == null) {
			return null;
		} else {
			return book;
		}
	}

	@Override
	public List<IBook> listBooks() {
		// TODO Auto-generated method stub
		List <IBook> bookList = new LinkedList<IBook>();
		for (int i = 1; i < bookMap.size()+1; i++) {
			bookList.add(bookMap.get(i));
		}
		return bookList;
	}

	@Override
	public List<IBook> findBooksByAuthor(String author) {
		// TODO Auto-generated method stub
		List <IBook> bookList = new LinkedList<IBook>();
		for (int i = 1; i < bookMap.size()+1; i++) {
			IBook book = bookMap.get(i);
			if (book.getAuthor().equals( author)) {
				bookList.add(book);
			}
		}
		return bookList;
	}

	@Override
	public List<IBook> findBooksByTitle(String title) {
		// TODO Auto-generated method stub
		List <IBook> bookList = new LinkedList<IBook>();
		for (int i = 1; i < bookMap.size()+1; i++) {
			IBook book = bookMap.get(i);
			if (book.getTitle().equals(title)) {
				bookList.add(book);
			}
		}
		return bookList;
	}

	@Override
	public List<IBook> findBooksByAuthorTitle(String author, String title) {
		// TODO Auto-generated method stub
		List <IBook> bookList = new LinkedList<IBook>();
		for (int i = 1; i < bookMap.size()+1; i++) {
			IBook book = bookMap.get(i);
			if (book.getTitle().equals(title)) {
				bookList.add(book);
			} else if (book.getAuthor().equals(author)) {
				bookList.add(book);
			}
		}
		return bookList;
	}

	public Map<Integer, IBook> getBookMap() {
		
		return bookMap;
	}
	
	private int getNextId() {
		return nextId++;
	}
}
