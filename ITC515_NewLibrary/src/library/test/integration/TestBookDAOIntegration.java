package library.test.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import library.daos.BookDAO;
import library.daos.BookHelper;
import library.daos.LoanDAO;
import library.daos.MemberDAO;
import library.entities.Book;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.IBookHelper;
import library.interfaces.daos.IMemberHelper;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestBookDAOIntegration {

	private IBook book1;
	private IBook book2;
	private ILoan loan;
	private IMember member;
	private String author;
	private String title;
	private String callNo;
	private String author2;
	private String title2;
	private String callNo2;
	private String firstName;
	private String lastName;
	private String contactPhone;
	private String emailAddress;
	private int id;
	Date borrowDate;
	Date dueDate;
	Calendar cal;
	IBook[] bookArray;
	BookDAO bookDAO;
	LoanDAO loanDAO;
	BookDAO memberDAO;
	IBookHelper helper;
	Map<Integer, IBook> bookMap;
	
	@Before
	public void setUp() {
		bookMap = new HashMap<Integer, IBook>();
		helper = new BookHelper();
		
		bookDAO = new BookDAO(helper, bookMap);
		
		author = "H. P. McNeal";
		title = "Deep and Meaingful Thoughts";
		callNo = "ABC11234";
		id = 1;
		
	
		book1 = helper.makeBook(author,title,callNo,id);
		book2 = helper.makeBook("author2", "title2", "callNo2", id+1);
		bookMap.put(id, book1);
		bookMap.put(id+1, book2);
	}
	
	
	@After
	public void tearDown() {
		bookDAO = null;
		
		helper = null;
		bookMap = null;
	}
	
	@Test
	public void testBookDAO() {
		
		
		BookDAO newBook = new BookDAO(helper);
		assertNotNull(newBook);
		assertTrue(newBook instanceof IBookDAO);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testBookDAONull() {
		bookDAO = new BookDAO(null);
		
		
	}
	
	@Test
	public void testAddBook() {

		
		
		
		
		IBook newBook = bookDAO.addBook(author, title, callNo);
				
		assertTrue(bookMap.size() == 2);
		assertEquals(book1.getID(), newBook.getID());
		assertEquals(bookMap.get(1), newBook);
		
		newBook = bookMap.get(1);
		//assertEquals(book1, newBook);
		
	}
	
	
	@Test
	public void testGetBookByIDPresent() {
	
		
		IBook newBook = bookDAO.addBook(author, title, callNo);
		
		newBook = bookDAO.getBookByID(id);
		
		assertEquals(bookMap.get(1), newBook);
		
	}
	
	@Test
	public void testGetBookByIDAbsent() {
		
		
		IBook newBook = bookDAO.addBook(author, title, callNo);
		
		newBook = bookDAO.getBookByID(id+10);
		
		assertNull(newBook);
		
	}
	
	
	@Test
	public void testFindBookByAuthor() {

		
		bookDAO.addBook(author, title, callNo);
		bookDAO.addBook("Author2", title, callNo);
		//book2 = helper.makeBook(author, title, callNo, id+1);
		
		List <IBook> list = bookDAO.findBooksByAuthor(author);
		
		assertTrue(list.size() == 1);
		assertEquals(bookMap.get(1), list.get(0));
		
		
	}
	
	@Test
	public void testFindBookByTitle() {
		String expectedTitle = "Title2";
		
		
		
		
		bookDAO.addBook(author, title, callNo);
		bookDAO.addBook(author, "title2", callNo);
		//book2 = helper.makeBook(author, expectedTitle, callNo, id);
		
		List <IBook> list = bookDAO.findBooksByTitle(title);
		
		assertTrue(list.size() == 1);
		assertEquals(bookMap.get(1), list.get(0));
		
		
	}
	
	
	@Test
	public void testFindBookByAuthorTitle() {
		String expectedTitle = "Title2";
		String expectedAuthor = "Author2";
		
		
		
		
		bookDAO.addBook(author, title, callNo);
		bookDAO.addBook("author2", "title2", callNo);
		
		List <IBook> list = bookDAO.findBooksByAuthorTitle(author, title);
		
		assertTrue(list.size() == 1);
		assertEquals(bookMap.get(1), list.get(0));
		
		
	}
	
	@Test
	public void testListBook() {
		List<IBook> list = bookDAO.listBooks();
		assertTrue(list.size() == 2);
		
				
		bookDAO.addBook(author, title, callNo);
		
		list = bookDAO.listBooks();
		assertTrue(list.size() == 2);
		IBook book = list.get(0);
		//book.getAuthor();
		
	}
	
}
