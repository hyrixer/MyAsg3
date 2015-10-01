package library.test.integration;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import library.daos.BookDAO;
import library.entities.Book;
import library.entities.Loan;
import library.entities.Member;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.IBookHelper;
import library.interfaces.entities.EBookState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestBookIntegration {

		
		private IBook book;
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
		IBookHelper helper;
		
			
			@Before
			public void setUp() throws Exception {
				author = "Steven Jacobs";
				title = "Quite a Large Book";
				callNo = "ABC12345";
				id = 1;
				firstName = "bob";
				lastName = "Stephans";
				contactPhone = "763863873";
				emailAddress = "AnEmailAddress@Email.com";
				cal = Calendar.getInstance();;
				borrowDate = cal.getTime();
				cal.setTime(borrowDate);
				cal.add(Calendar.DATE, ILoan.LOAN_PERIOD);
				dueDate = cal.getTime();
				book = new Book(author, title, callNo, id);
				member = new Member(firstName,lastName,contactPhone,emailAddress,1);
				loan = new Loan(book, member, borrowDate, borrowDate);
			}
			
			@After
			public void tearDown() throws Exception {
				book = null;
				member = null;
				loan = null;
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
				
				
				book.borrow(loan);
				
				EBookState expectedState = EBookState.ON_LOAN;
				EBookState actualState = book.getState();
				assertEquals(expectedState, actualState);
			}
			@Test
			public void testBorrowAndGetLoan() {
				
				
				book.borrow(loan);
				
				ILoan newLoan = book.getLoan();
				assertNotNull(loan);
				assertEquals(loan, newLoan);
			}
			
			@Test (expected = IllegalArgumentException.class)
			public void testBookReturnNotOnLoan() {
				// return an available book
				book.returnBook(true);
				
			}
			
			@Test
			public void testBookReturnUndamaged() {
				
				
				book.borrow(loan);
				book.returnBook(false);
				
				ILoan newloan = book.getLoan();
				assertNull(newloan);
				
				EBookState expectedState = EBookState.AVAILABLE;
				EBookState actualState = book.getState();
				assertEquals(expectedState, actualState);
			}
			
			@Test
			public void testBookReturnDamaged() {
				
				
				book.borrow(loan);
				book.returnBook(true);
				
				ILoan newLoan = book.getLoan();
				assertNull(newLoan);
				
				EBookState expectedState = EBookState.DAMAGED;
				EBookState actualState = book.getState();
				assertEquals(expectedState, actualState);
			}
			
			@Test
			public void testLose() {
				
				
				book.borrow(loan);
				book.lose();
				
				EBookState expectedState = EBookState.LOST;
				EBookState actualState = book.getState();
				assertEquals(expectedState, actualState);
				
			}
			
			@Test (expected = IllegalArgumentException.class)
			public void testLoseAvailable() {
				
				
				book.borrow(loan);
				// return a book.
				book.returnBook(false);
				//test lose on a available book
				book.lose();
				
				
				
			}
			
			@Test
			public void testRepair() {
				
				
				book.borrow(loan);
				//return a damaged book
				book.returnBook(true);
				
				book.repair();
				
				EBookState expectedState = EBookState.AVAILABLE;
				EBookState actualState = book.getState();
				assertEquals(expectedState, actualState);
				
				
				
			}
			
			@Test(expected = IllegalArgumentException.class)
			public void testRepairNotDamaged() {

				
				book.borrow(loan);
				//return a not damaged book
				book.returnBook(false);
				
				book.repair();
				
				
			}
			
			@Test
			public void testDisposeAvailable() {
				
				
				book.borrow(loan);
				//return book undamaged
				book.returnBook(false);
				
				book.dispose();
				
				EBookState expectedState = EBookState.DISPOSED;
				EBookState actualState = book.getState();
				assertEquals(expectedState, actualState);
			}
			
			@Test
			public void testDisposeDamaged() {
				
				
				book.borrow(loan);
				//return a damaged book
				book.returnBook(true);
				
				book.dispose();
				
				EBookState expectedState = EBookState.DISPOSED;
				EBookState actualState = book.getState();
				assertEquals(expectedState, actualState);
			}
			
			@Test
			public void testDisposeLost() {
								
				book.borrow(loan);
				//lose the book
				book.lose();
				
				book.dispose();
				
				EBookState expectedState = EBookState.DISPOSED;
				EBookState actualState = book.getState();
				assertEquals(expectedState, actualState);
			}

			@Test  (expected = IllegalArgumentException.class)
			public void testDisposeOnLoan() {
		
				
				book.borrow(loan);
			
				book.dispose();
				
			}
			@Test 
			public void testGetState() {
				
				//set state to on loan so we have a known state
				book.borrow(loan);
				
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

