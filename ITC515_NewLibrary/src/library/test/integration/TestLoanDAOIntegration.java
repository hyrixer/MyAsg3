package library.test.integration;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import library.daos.BookDAO;
import library.daos.LoanDAO;
import library.daos.LoanHelper;
import library.entities.Book;
import library.entities.Loan;
import library.entities.Member;
import library.interfaces.daos.IBookHelper;
import library.interfaces.daos.ILoanHelper;
import library.interfaces.entities.EBookState;
import library.interfaces.entities.ELoanState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestLoanDAOIntegration {
	
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
	LoanDAO loanDAO;
	ILoanHelper helper;
	Map<Integer, ILoan> loanMap;
	
		
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
			borrowDate = new Date();
			cal.setTime(borrowDate);
			cal.add(Calendar.DATE, ILoan.LOAN_PERIOD);
			dueDate = cal.getTime();
			helper = new LoanHelper();
			loanMap = new HashMap<Integer, ILoan>();
			
			loanDAO = new LoanDAO(helper, loanMap);
			book = new Book(author, title, callNo, id);
			member = new Member(firstName,lastName,contactPhone,emailAddress,1);
			loan = new Loan(book, member, borrowDate, dueDate);
			loanMap.put(id, loan);
		}
		
		@After
		public void tearDown() throws Exception {
			book = null;
			member = null;
			loan = null;
			loanDAO = null;
			loanMap = null;
		}
	
		@Test
		public void testGetLoanById() {
			
			ILoan newLoan = loanDAO.getLoanByID(1);
			assertEquals(newLoan, loan);
			
		}

		@Test
		public void testListLoans() {
			
			List<ILoan> newList = loanDAO.listLoans();
			assertTrue(newList.size() == 1);
			assertEquals(newList.get(0), loan);
			
		}
		
		@Test
		public void testFindLoansByBorrower() {
			
			List<ILoan> newList = loanDAO.findLoansByBorrower(member);
			assertTrue(newList.size() == 1);
			assertEquals(newList.get(0), loan);
			
		}
		
		@Test
		public void testFindLoansByBookTitle() {
			
			List<ILoan> newList = loanDAO.findLoansByBookTitle(title);
			assertTrue(newList.size() == 1);
			assertEquals(newList.get(0), loan);
			
		}
		
		@Test
		public void testCreateLoan() {
			ILoan newLoan = loanDAO.createLoan(member, book);
			IBook newBook = newLoan.getBook();
			IMember newMember = newLoan.getBorrower();
			assertTrue(loanDAO.listLoans().size() == 1);
			assertEquals(newBook, book);
			assertEquals(newMember, member);
			//assertEquals(newLoan, loanMap.get(1));
			
		}
		
		@Test
		public void testCommitLoan() {
			loanDAO.commitLoan(loan);
			List<ILoan> newLoans = loanDAO.listLoans();
			assertTrue(newLoans.size() == 1);
			assertEquals(newLoans.get(0), loan);
		}
		
		
}
