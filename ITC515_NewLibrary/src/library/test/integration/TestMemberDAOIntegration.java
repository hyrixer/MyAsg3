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
import library.daos.MemberDAO;
import library.daos.MemberHelper;
import library.entities.Book;
import library.entities.Loan;
import library.entities.Member;
import library.interfaces.daos.ILoanHelper;
import library.interfaces.daos.IMemberHelper;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;



public class TestMemberDAOIntegration {
	private IBook book;
	
	private ILoan loan;
	private IMember member;
	private IMember member2;
	private String author;
	private String title;
	private String callNo;
	
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
	MemberDAO memberDAO;
	IMemberHelper helper;
	Map<Integer, IMember> memberMap;
	
		
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
			helper = new MemberHelper();
			memberMap = new HashMap<Integer, IMember>();
			
			memberDAO = new MemberDAO(helper, memberMap);
			book = new Book(author, title, callNo, id);
			member = new Member(firstName,lastName,contactPhone,emailAddress,1);
			member2 = new Member("first", "last", "phone", "email", 2);
			loan = new Loan(book, member, borrowDate, dueDate);
			memberMap.put(id, member);
			memberMap.put(id+1, member2);
		}
		
		@After
		public void tearDown() throws Exception {
			book = null;
			member = null;
			loan = null;
			memberDAO = null;
			memberMap = null;
		}
	
		@Test
		public void testAddMember() {
			IMember newMember = memberDAO.addMember(firstName,lastName,contactPhone,emailAddress);
			assertEquals(newMember, memberMap.get(1));
		
		
		}
		
		@Test
		public void testListMembers() {
			List<IMember> newList = memberDAO.listMembers();
			assertTrue(newList.size() == 2);
			assertEquals(newList.get(0), member);
			assertEquals(newList.get(1), member2);
		}
		
		@Test
		public void testFindMemberByLastNAme() {
			List<IMember> newList = memberDAO.findMembersByLastName(lastName);
			assertTrue(newList.size() == 1);
			assertEquals(newList.get(0), member);
			
		}
		
		@Test
		public void testFindMemberByEmail() {
			List<IMember> newList = memberDAO.findMembersByEmailAddress(emailAddress);
			assertTrue(newList.size() == 1);
			assertEquals(newList.get(0), member);
		}
		
		@Test
		public void testFindMemberByNAmes() {
			List<IMember> newList = memberDAO.findMembersByNames(firstName, lastName);
			assertTrue(newList.size() == 1);
			assertEquals(newList.get(0), member);
		}
		
}
