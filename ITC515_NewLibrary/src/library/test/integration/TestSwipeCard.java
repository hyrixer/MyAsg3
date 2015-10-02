package library.test.integration;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.*;

import library.BorrowUC_CTL;
import library.BorrowUC_UI;
import library.daos.BookDAO;
import library.daos.BookHelper;
import library.daos.LoanDAO;
import library.daos.LoanHelper;
import library.daos.MemberHelper;
import library.daos.MemberDAO;
import library.hardware.*;
import library.hardware.Scanner;
import library.interfaces.EBorrowState;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.IBookHelper;
import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.ILoanHelper;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.daos.IMemberHelper;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;
import library.interfaces.hardware.ICardReader;
import library.interfaces.hardware.IDisplay;
import library.interfaces.hardware.IPrinter;
import library.interfaces.hardware.IScanner;
import library.panels.borrow.ABorrowPanel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestSwipeCard {

	ICardReader reader;
	IScanner scanner;
	IPrinter printer;
	IDisplay display;
	BorrowUC_CTL ctl;
	IBookDAO bookDao;
	ILoanDAO loanDao;
	IMemberDAO memberDao;
	IBookHelper bookHelper = new BookHelper();
	ILoanHelper loanHelper = new LoanHelper();
	IMemberHelper helper;
	ABorrowPanel ui;
	
	
	
	
	@Before
	public void setUp() throws Exception {
		reader = mock(ICardReader.class);
		scanner = mock(IScanner.class);
		printer = mock(IPrinter.class);
		display = mock(IDisplay.class);
		bookDao = new BookDAO(bookHelper);
		loanDao = new LoanDAO(loanHelper);
		memberDao = mock(IMemberDAO.class);
		helper = mock(IMemberHelper.class);
		ui = mock(ABorrowPanel.class);
		//reader.addListener(ctl);
		ctl = new BorrowUC_CTL(reader, scanner, printer, display, 
				bookDao, loanDao, memberDao, ui);
		ctl.initialise();
	}

	@After
	public void tearDown() throws Exception {
		reader = null;
		scanner = null;
		printer = null;
		display = null;
		bookDao = null;
		loanDao = null;
		memberDao = null;
		ctl = null;
		
	}

	@Test
	public void testSwipeCardMemberAvailableBorrowNotRestricted() {
		
		IMember member = mock(IMember.class);
		ILoan loan = mock(ILoan.class);
		List<ILoan> mockList = mock(List.class);
		Iterator<ILoan> mockIterator = mock(Iterator.class);
		int id = 1;
		
		
		when(mockList.iterator()).thenReturn(mockIterator);
		when(mockIterator.hasNext()).thenReturn(true, false);
		when(mockIterator.next()).thenReturn(loan);
		when(memberDao.getMemberByID(id)).thenReturn(member);
		when(member.hasOverDueLoans()).thenReturn(false);
		when(member.hasReachedLoanLimit()).thenReturn(false);
		when(member.hasFinesPayable()).thenReturn(false);
		when(member.hasReachedFineLimit()).thenReturn(false);
		when(member.getLoans()).thenReturn(mockList);
		
		when(loan.toString()).thenReturn("loanDetails");
		
		when(display.getDisplay()).thenReturn(null);
		
		ctl.cardSwiped(id);
		
		verify(memberDao).getMemberByID(id);
		verify(member).hasOverDueLoans();
		verify(member).hasReachedLoanLimit();
		verify(member).hasFinesPayable();
		verify(member).hasReachedFineLimit();
		EBorrowState actualState = ctl.getState();
		assertEquals(EBorrowState.SCANNING_BOOKS, actualState);
		
		verify(reader).setEnabled(false);
	
		verify(ui).setState(EBorrowState.SCANNING_BOOKS);
		verify(ui).displayScannedBookDetails("");
		verify(ui).displayPendingLoan("");
		
	
		verify(ui).displayExistingLoan("loanDetails");
		
		
		
		
	}
	
	@Test
	public void testSwipeCardMemberAvailableBorrowRestricted() {
		
		IMember member = mock(IMember.class);
		ILoan loan = mock(ILoan.class);
		List<ILoan> mockList = mock(List.class);
		Iterator<ILoan> mockIterator = mock(Iterator.class);
		int id = 1;
		
		
		when(mockList.iterator()).thenReturn(mockIterator);
		when(mockIterator.hasNext()).thenReturn(true, false);
		when(mockIterator.next()).thenReturn(loan);
		when(memberDao.getMemberByID(id)).thenReturn(member);
		when(member.hasOverDueLoans()).thenReturn(true);
		when(member.hasReachedLoanLimit()).thenReturn(false);
		when(member.hasFinesPayable()).thenReturn(false);
		when(member.hasReachedFineLimit()).thenReturn(false);
		when(member.getLoans()).thenReturn(mockList);
		
		when(loan.toString()).thenReturn("loanDetails");
		
		when(display.getDisplay()).thenReturn(null);
		
		ctl.cardSwiped(id);
		
		verify(memberDao).getMemberByID(id);
		verify(member).hasOverDueLoans();
		verify(member).hasReachedLoanLimit();
		verify(member).hasFinesPayable();
		verify(member).hasReachedFineLimit();
		EBorrowState actualState = ctl.getState();
		assertEquals(EBorrowState.BORROWING_RESTRICTED, actualState);
		
		verify(reader).setEnabled(false);
	
		verify(ui).setState(EBorrowState.BORROWING_RESTRICTED);
		
		
	
		verify(ui).displayExistingLoan("loanDetails");
		
		
		
		
	}
	
		
	

}
