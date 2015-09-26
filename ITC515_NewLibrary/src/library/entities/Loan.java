package library.entities;

import java.util.Date;

import library.interfaces.entities.ELoanState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

public class Loan implements ILoan{
	
	int id;
	Date borrowDate;
	Date dueDate;
	IMember borrower;
	IBook book;
	ELoanState state;
	
	public Loan (IBook book, IMember borrower, Date borrowDate, Date dueDate) {
		if (book == null ) {
			throw new IllegalArgumentException("book cannot be null");
		}
		else if (borrower == null ) {
			throw new IllegalArgumentException("borrow cannot be null");
		}
		else if (borrowDate == null) {
			throw new IllegalArgumentException("borrow date cannot be null");
		}
		else if (dueDate == null) {
			throw new IllegalArgumentException("due date cannot be null");
		}
		else if (dueDate.before(borrowDate)) {
			throw new IllegalArgumentException("due dtae cannot be before borrow date");
		}  else {
			this.book = book;
			this.borrower = borrower;
			this.dueDate = dueDate;
			this.borrowDate = borrowDate;
			this.id = 0;
		}
	}

	@Override
	public void commit(int id) {
		// TODO Auto-generated method stub
		if (state != ELoanState.PENDING) {
			throw new RuntimeException("loan is not pending");
		 
			
		} else if (id <= 0) {
			throw new IllegalArgumentException("id cannot be less than 0");
		}
		else {
			state = ELoanState.CURRENT;
			book.borrow(this);
			borrower.addLoan(this);
		}
	}

	@Override
	public void complete() {
		// TODO Auto-generated method stub
		if (state == ELoanState.CURRENT || state == ELoanState.OVERDUE) {
			state = ELoanState.COMPLETE;
		} else {
			throw new IllegalArgumentException("cannot complete at this time");
		}
	}

	@Override
	public boolean isOverDue() {
		// TODO Auto-generated method stub
		if (state == ELoanState.OVERDUE) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkOverDue(Date currentDate) {
		// TODO Auto-generated method stub
		if (state == ELoanState.CURRENT || state == ELoanState.OVERDUE) {
			if (currentDate.after(dueDate)) {
				state = ELoanState.OVERDUE;
				return true;
			}
		} else {
			throw new IllegalArgumentException("book is not on loan");
		}
		
		return false;
	}
	
	@Override
	public boolean isCurrent() {
		// TODO Auto-generated method stub
		if (state == ELoanState.CURRENT){
			return true;
		} else {
			return false;
		}
	}


	@Override
	public IMember getBorrower() {
		// TODO Auto-generated method stub
		return borrower;
	}

	@Override
	public IBook getBook() {
		// TODO Auto-generated method stub
		return book;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return id;
	}

	
}
