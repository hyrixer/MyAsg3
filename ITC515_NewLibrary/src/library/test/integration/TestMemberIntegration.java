package library.test.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


import library.daos.BookDAO;
import library.entities.Book;
import library.entities.Loan;
import library.entities.Member;
import library.interfaces.daos.IBookHelper;
import library.interfaces.entities.ELoanState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestMemberIntegration {

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
		public void testGetLoansAndRemoveLoan() {
			
			member.addLoan(loan);
			List<ILoan> loans = member.getLoans();
			assertTrue(loans.size() == 1);
			assertEquals(loans.get(0), loan);
			member.removeLoan(loan);
			loans = member.getLoans();
			assertTrue(loans.size() == 0);
			
			
		}
		
		
		
		
}


