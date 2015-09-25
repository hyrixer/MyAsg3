package library.entities;

import library.interfaces.entities.EBookState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;

public class Book implements IBook{

	String author;
	String title;
	String callNumber;
	int id;
	ILoan loan;
	EBookState state;
	
	public Book (String author, String title, String callNumber, int bookID) {
		if (author == null || author == "") {
			throw new IllegalArgumentException("author cannot be null");
		}
		else if (title == null || title == "") {
			throw new IllegalArgumentException("title cannot be null");
		}
		else if (callNumber == null || callNumber == "") {
			throw new IllegalArgumentException("call number cannot be null");
		}
		else if (bookID <= 0) {
			throw new IllegalArgumentException("id cannot be zero or below");
		} else {
			this.author = author; 
			this.title = title;
			this.callNumber = callNumber;
			this.id = bookID;
		}
		
	}
	
	@Override
	public void borrow(ILoan loan) {
		// TODO Auto-generated method stub
		if (state != EBookState.AVAILABLE) {
			throw new IllegalArgumentException("book is not available");
		} else {
			this.loan = loan;
		}
	}

	@Override
	public ILoan getLoan() {
		// TODO Auto-generated method stub
		return loan;
	}

	@Override
	public void returnBook(boolean damaged) {
		// TODO Auto-generated method stub
		if (state != EBookState.ON_LOAN) {
			throw new IllegalArgumentException("book is not on loan");
		} else {
			this.loan = null;
			if (damaged) {
				state = EBookState.DAMAGED;
			} else {
				state = EBookState.AVAILABLE;
			}
		}
		
	}

	@Override
	public void lose() {
		// TODO Auto-generated method stub
		if (state != EBookState.ON_LOAN) {
			throw new IllegalArgumentException("book is not on loan");
		} else {
			state = EBookState.LOST;
		}
	}
	
	@Override
	public void repair() {
		// TODO Auto-generated method stub
		if (state != EBookState.DAMAGED) {
			throw new IllegalArgumentException("book is not damaged");
		} else {
			state = EBookState.AVAILABLE;
		}
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		if (state == EBookState.AVAILABLE || state == EBookState.DAMAGED || state == EBookState.LOST ) {
			state = EBookState.DISPOSED;
		} else {
			throw new IllegalArgumentException("book cannot be disposed at this time");
		}
	}
	
	@Override
	public EBookState getState() {
		// TODO Auto-generated method stub
		return state;
	}

	@Override
	public String getAuthor() {
		// TODO Auto-generated method stub
		return author;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return title;
	}

	@Override
	public String getCallNumber() {
		// TODO Auto-generated method stub
		return callNumber;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return id;
	}

}
