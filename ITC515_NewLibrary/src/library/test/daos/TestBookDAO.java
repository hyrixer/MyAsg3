package library.test.daos;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import library.daos.BookDAO;
import library.daos.BookHelper;
import library.entities.Book;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.IBookHelper;
import library.interfaces.entities.EBookState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;

import org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TestBookDAO {

	private BookDAO bookDAO;
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
		bookHelper = mock(IBookHelper.class);
		book1 = mock(IBook.class);
		book2 = mock(IBook.class);
		bookDAO = new BookDAO(bookHelper);
		author = "H. P. McNeal";
		title = "Deep and Meaingful Thoughts";
		callNo = "ABC11234";
		id = 1;
	}
	
	
	@After
	public void tearDown() {
		bookDAO = null;
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

		
		when(bookHelper.makeBook(author, title, callNo, id)).thenReturn(book1);
		
		
		IBook newBook = bookDAO.addBook(author, title, callNo);
		bookMap = bookDAO.getBookMap();
		
		assertTrue(bookMap.size() == 1);
		assertEquals(book1, newBook);
		
		newBook = bookMap.get(1);
		assertEquals(book1, newBook);
		
	}
	
	
	@Test
	public void testGetBookByIDPresent() {
		when(bookHelper.makeBook(author, title, callNo, id)).thenReturn(book1);
		when(book1.getID()).thenReturn(id);
		
		IBook newBook = bookDAO.addBook(author, title, callNo);
		
		newBook = bookDAO.getBookByID(id);
		
		assertEquals(book1, newBook);
		
	}
	
	@Test
	public void testGetBookByIDAbsent() {
		when(bookHelper.makeBook(author, title, callNo, id)).thenReturn(book1);
		when(book1.getID()).thenReturn(id);
		
		IBook newBook = bookDAO.addBook(author, title, callNo);
		
		newBook = bookDAO.getBookByID(id+1);
		
		assertNull(newBook);
		
	}
	
	
	@Test
	public void testFindBookByAuthor() {
		String expectedAuthor = "B. Arther";
		when(bookHelper.makeBook(author, title, callNo, id)).thenReturn(book1);
		when(bookHelper.makeBook(expectedAuthor, title, callNo, id+1)).thenReturn(book2);
		
		when(book1.getAuthor()).thenReturn(author);
		when(book2.getAuthor()).thenReturn(expectedAuthor);
		
		bookDAO.addBook(author, title, callNo);
		bookDAO.addBook(expectedAuthor, title, callNo);
		
		List <IBook> list = bookDAO.findBooksByAuthor(expectedAuthor);
		
		assertTrue(list.size() == 1);
		assertEquals(book2, list.get(0));
		
		
	}
	
	@Test
	public void testFindBookByTitle() {
		String expectedTitle = "Book About Nothing";
		when(bookHelper.makeBook(author, title, callNo, id)).thenReturn(book1);
		when(bookHelper.makeBook(author, expectedTitle, callNo, id+1)).thenReturn(book2);
		
		when(book1.getTitle()).thenReturn(title);
		when(book2.getTitle()).thenReturn(expectedTitle);
		
		bookDAO.addBook(author, title, callNo);
		bookDAO.addBook(author, expectedTitle, callNo);
		
		List <IBook> list = bookDAO.findBooksByTitle(expectedTitle);
		
		assertTrue(list.size() == 1);
		assertEquals(book2, list.get(0));
		
		
	}
	
	
	@Test
	public void testFindBookByAuthorTitle() {
		String expectedTitle = "Deep and Meaingful Thoughts2";
		String expectedAuthor = "B. Arther";
		when(bookHelper.makeBook(author, title, callNo, id)).thenReturn(book1);
		when(bookHelper.makeBook(expectedAuthor, expectedTitle, callNo, id+1)).thenReturn(book2);
		
		when(book1.getTitle()).thenReturn(title);
		when(book1.getAuthor()).thenReturn(author);
		when(book2.getTitle()).thenReturn(expectedTitle);
		when(book2.getAuthor()).thenReturn(expectedAuthor);
		
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
		
		when(bookHelper.makeBook(author, title, callNo, id)).thenReturn(book1);
		
		bookDAO.addBook(author, title, callNo);
		
		list = bookDAO.listBooks();
		assertTrue(list.size() == 1);
		
	}
	
}
