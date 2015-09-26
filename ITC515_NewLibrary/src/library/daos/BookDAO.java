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
	int nextId = 0;
	Map<Integer, IBook> bookMap = new HashMap<Integer, IBook>();
	
	
	public BookDAO (IBookHelper helper) {
		if (helper == null) {
			throw new IllegalArgumentException("helper cannot be null");
		} else {
			this.helper = helper;
		}
		
	}
	
	@Override
	public IBook addBook(String author, String title, String callNo) {
		// TODO Auto-generated method stub
		IBook newBook = helper.makeBook(author, title, callNo, nextId);
		bookMap.put(nextId, newBook);
		nextId++;
		
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
		for (int i = 0; i < bookMap.size(); i++) {
			bookList.add(bookMap.get(i));
		}
		return bookList;
	}

	@Override
	public List<IBook> findBooksByAuthor(String author) {
		// TODO Auto-generated method stub
		List <IBook> bookList = new LinkedList<IBook>();
		for (int i = 0; i < bookMap.size(); i++) {
			IBook book = bookMap.get(i);
			if (book.getAuthor() == author) {
				bookList.add(book);
			}
		}
		return bookList;
	}

	@Override
	public List<IBook> findBooksByTitle(String title) {
		// TODO Auto-generated method stub
		List <IBook> bookList = new LinkedList<IBook>();
		for (int i = 0; i < bookMap.size(); i++) {
			IBook book = bookMap.get(i);
			if (book.getTitle() == title) {
				bookList.add(book);
			}
		}
		return bookList;
	}

	@Override
	public List<IBook> findBooksByAuthorTitle(String author, String title) {
		// TODO Auto-generated method stub
		List <IBook> bookList = new LinkedList<IBook>();
		for (int i = 0; i < bookMap.size(); i++) {
			IBook book = bookMap.get(i);
			if (book.getTitle() == title) {
				bookList.add(book);
			} else if (book.getAuthor() == author) {
				bookList.add(book);
			}
		}
		return bookList;
	}

}
