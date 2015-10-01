package library.test.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import library.daos.BookDAO;
import library.daos.BookHelper;
import library.entities.Book;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.IBookHelper;
import library.interfaces.entities.IBook;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestBookDAOIntegration {

	private BookDAO bookDAO;
	private BookDAO bookDAO2;
	private int id;
	private String author;
	private String title;
	private String callNo;
	private IBookHelper bookHelper;
	private Map<Integer, IBook> bookMap;
	private IBook book1;
	private IBook book2;
	
	@Before
	public void setUp() {
		bookMap = new HashMap<Integer, IBook>();
		bookHelper = new BookHelper();
		
		bookDAO = new BookDAO(bookHelper);
		bookDAO2 = new BookDAO(bookHelper);
		author = "H. P. McNeal";
		title = "Deep and Meaingful Thoughts";
		callNo = "ABC11234";
		id = 1;
		//book1 = new Book(author,title,callNo,1);
		book1 =  bookHelper.makeBook(author,title,callNo,id);
		book2 = bookHelper.makeBook(author, title, callNo, id);
	}
	
	
	@After
	public void tearDown() {
		bookDAO = null;
		bookDAO2 = null;
		bookHelper = null;
		bookMap = null;
	}
	
	@Test
	public void testBookDAO() {
		
		
		BookDAO newBook = new BookDAO(bookHelper);
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
		bookMap = bookDAO.getBookMap();
		
		assertTrue(bookMap.size() == 1);
		assertEquals(book1.getID(), newBook.getID());
		assertEquals(book1, newBook);
		
		newBook = bookMap.get(1);
		assertEquals(book1, newBook);
		
	}
	
	
	@Test
	public void testGetBookByIDPresent() {
	
		
		IBook newBook = bookDAO.addBook(author, title, callNo);
		
		newBook = bookDAO.getBookByID(id);
		
		assertEquals(book1, newBook);
		
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
		book2 = bookHelper.makeBook("author2", title, callNo, id+1);
		
		List <IBook> list = bookDAO.findBooksByAuthor("Author2");
		
		assertTrue(list.size() == 1);
		assertEquals(book2, list.get(0));
		
		
	}
	
	@Test
	public void testFindBookByTitle() {
		String expectedTitle = "Title2";
		
		
		
		
		bookDAO.addBook(author, title, callNo);
		bookDAO.addBook(author, expectedTitle, callNo);
		book2 = bookHelper.makeBook(author, expectedTitle, callNo, id);
		
		List <IBook> list = bookDAO.findBooksByTitle(expectedTitle);
		
		assertTrue(list.size() == 1);
		assertEquals(book2, list.get(0));
		
		
	}
	
	
	@Test
	public void testFindBookByAuthorTitle() {
		String expectedTitle = "Title2";
		String expectedAuthor = "Author2";
		
		
		
		
		bookDAO.addBook(author, title, callNo);
		bookDAO.addBook(expectedAuthor, expectedTitle, callNo);
		
		List <IBook> list = bookDAO.findBooksByAuthorTitle(expectedAuthor, expectedTitle);
		
		assertTrue(list.size() == 1);
		assertEquals(book2, list.get(0));
		
		
	}
	
	@Test
	public void testListBook() {
		List<IBook> list = bookDAO.listBooks();
		assertTrue(list.size() == 0);
		
				
		bookDAO.addBook(author, title, callNo);
		
		list = bookDAO.listBooks();
		assertTrue(list.size() == 1);
		IBook book = list.get(0);
		book.getAuthor();
		
	}
	
}
