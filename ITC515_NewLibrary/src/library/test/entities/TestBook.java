package library.test.entities;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import library.entities.Book;
import library.interfaces.entities.EBookState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;

import org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestBook {
	
	private IBook book;
	private String author;
	private String title;
	private String callNo;
	private int id;
	
	@Before
	public void setUp() throws Exception {
		author = "Steven Jacobs";
		title = "Quite a Large Book";
		callNo = "ABC12345";
		id = 1;
		book = new Book(author, title, callNo, id);
	}
	
	@After
	public void tearDown() throws Exception {
		book = null;
	}
	
	@Test
	public void testBook() {
		IBook test = new Book(author, title, callNo, id);
		assertNotNull(test);
		assertTrue(test instanceof IBook);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testBookNullAuthor() {
		book = new Book (null, title, callNo, id);
	}
	
	
	@Test
	public void testBorrow () {
		ILoan mockLoan = mock(ILoan.class);
		
		book.borrow(mockLoan);
		
		EBookState expectedState = EBookState.ON_LOAN;
		EBookState actualState = book.getState();
		assertEquals(expectedState, actualState);
	}
	@Test
	public void testBorrowAndGetLoan() {
		ILoan mockLoan = mock(ILoan.class);
		
		book.borrow(mockLoan);
		
		ILoan loan = book.getLoan();
		assertNotNull(loan);
		assertEquals(mockLoan, loan);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testBookReturnNotOnLoan() {
		// return an available book
		book.returnBook(true);
		
	}
	
	@Test
	public void testBookReturnUndamaged() {
		ILoan mockLoan = mock(ILoan.class);
		
		book.borrow(mockLoan);
		book.returnBook(false);
		
		ILoan loan = book.getLoan();
		assertNull(loan);
		
		EBookState expectedState = EBookState.AVAILABLE;
		EBookState actualState = book.getState();
		assertEquals(expectedState, actualState);
	}
	
	@Test
	public void testBookReturnDamaged() {
		ILoan mockLoan = mock(ILoan.class);
		
		book.borrow(mockLoan);
		book.returnBook(true);
		
		ILoan loan = book.getLoan();
		assertNull(loan);
		
		EBookState expectedState = EBookState.DAMAGED;
		EBookState actualState = book.getState();
		assertEquals(expectedState, actualState);
	}
	
	@Test
	public void testLose() {
		ILoan mockLoan = mock(ILoan.class);
		
		book.borrow(mockLoan);
		book.lose();
		
		EBookState expectedState = EBookState.LOST;
		EBookState actualState = book.getState();
		assertEquals(expectedState, actualState);
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testLoseAvailable() {
		ILoan mockLoan = mock(ILoan.class);
		
		book.borrow(mockLoan);
		// return a book.
		book.returnBook(false);
		//test lose on a available book
		book.lose();
		
		
		
	}
	
	@Test
	public void testRepair() {
		ILoan mockLoan = mock(ILoan.class);
		
		book.borrow(mockLoan);
		//return a damaged book
		book.returnBook(true);
		
		book.repair();
		
		EBookState expectedState = EBookState.AVAILABLE;
		EBookState actualState = book.getState();
		assertEquals(expectedState, actualState);
		
		
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRepairNotDamaged() {
		ILoan mockLoan = mock(ILoan.class);
		
		book.borrow(mockLoan);
		//return a not damaged book
		book.returnBook(false);
		
		book.repair();
		
		
	}
	
	@Test
	public void testDisposeAvailable() {
		ILoan mockLoan = mock(ILoan.class);
		
		book.borrow(mockLoan);
		//return book undamaged
		book.returnBook(false);
		
		book.dispose();
		
		EBookState expectedState = EBookState.DISPOSED;
		EBookState actualState = book.getState();
		assertEquals(expectedState, actualState);
	}
	
	@Test
	public void testDisposeDamaged() {
		ILoan mockLoan = mock(ILoan.class);
		
		book.borrow(mockLoan);
		//return a damaged book
		book.returnBook(true);
		
		book.dispose();
		
		EBookState expectedState = EBookState.DISPOSED;
		EBookState actualState = book.getState();
		assertEquals(expectedState, actualState);
	}
	
	@Test
	public void testDisposeLost() {
		ILoan mockLoan = mock(ILoan.class);
		
		book.borrow(mockLoan);
		//lose the book
		book.lose();
		
		book.dispose();
		
		EBookState expectedState = EBookState.DISPOSED;
		EBookState actualState = book.getState();
		assertEquals(expectedState, actualState);
	}

	@Test  (expected = IllegalArgumentException.class)
	public void testDisposeOnLoan() {
		ILoan mockLoan = mock(ILoan.class);
		
		book.borrow(mockLoan);
	
		book.dispose();
		
	}
	@Test 
	public void testGetState() {
		ILoan mockLoan = mock (ILoan.class);
		//set state to on loan so we have a known state
		book.borrow(mockLoan);
		
		//test that getState() returns the same as our known state
		EBookState expectedState = EBookState.ON_LOAN;
		EBookState actualState = book.getState();
		assertEquals(expectedState, actualState);
	}
	
	@Test
	public void testGetAuthor() {
		
		String newAuthor = book.getAuthor();
		
		assertEquals(newAuthor, author);
		
	}
	
	@Test
	public void testGetTitle() {
		
		String newTitle = book.getTitle();
		
		assertEquals(newTitle, title);
		
	}
	
	@Test
	public void testGetCallNumber() {
		
		String newCallNo = book.getCallNumber();
		
		assertEquals(newCallNo, callNo);
		
	}
	
	@Test
	public void testGetId() {
		
		int newId = book.getID();
		
		assertEquals(newId, id);
		
	}
	
}
