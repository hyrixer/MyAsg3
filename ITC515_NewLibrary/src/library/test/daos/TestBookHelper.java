package library.test.daos;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import library.daos.BookHelper;
import library.entities.Book;
import library.interfaces.daos.IBookHelper;
import library.interfaces.entities.IBook;

import org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestBookHelper {
	IBookHelper helper;
	IBook book1;
	String author;
	String title;
	String callNo;
	int id;
	
	
	@Before
	public void setUp() {
		helper = mock(IBookHelper.class);
		author = "A. Bookwriter";
		title = "Classic Literature";
		callNo = "AAA 26253";
		id = 1;
		book1 = mock(IBook.class);
				
	}
	@After
	public void tearDown() {
		helper = null;
		
	}
	
	@Test
	public void testBookHelper() {
		when(helper.makeBook(author, title, callNo, id)).thenReturn(book1);
		IBook newBook = helper.makeBook(author, title, callNo, id);
		assertNotNull(book1);
		assertTrue(newBook instanceof IBook);
		assertEquals(book1, newBook);
	}
}
