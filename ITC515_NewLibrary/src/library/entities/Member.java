package library.entities;

import java.util.Calendar;
import java.util.List;

import library.interfaces.entities.EMemberState;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

import java.util.Date;

public class Member implements IMember {

	int id;
	float totalFines;
	List<ILoan> loanList; 
	String firstName;
	String lastName;
	String contactPhone;
	String emailAddress;
	EMemberState state;
	
	
	
	
	public Member(String firstName, String lastName, String contactPhone, String emailAddress, int id) {
		
		if (firstName == null || firstName == "") {
			throw new IllegalArgumentException("first name cannot be null");
		}
		else if (lastName == null || lastName == "") {
			throw new IllegalArgumentException("last name cannot be null");
		}
		else if (contactPhone == null || contactPhone == "") {
			throw new IllegalArgumentException("contact phone cannot be null");
		}
		else if (emailAddress == null || emailAddress == "") {
			throw new IllegalArgumentException("email address cannot be null");
		}
		else if (id <= 0) {
			throw new IllegalArgumentException("id cannot be zero or below");
		} else {
			this.firstName = firstName;
			this.lastName = lastName;
			this.contactPhone = contactPhone;
			this.emailAddress = emailAddress;
			this.id = id;
		}
	}

	@Override
	public boolean hasOverDueLoans() {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		for (int i = 0; i <loanList.size(); i++) {
			if (loanList.get(i).checkOverDue(now)) {
			return true;
			}
		}
		return false;
	}

	@Override
	public boolean hasReachedLoanLimit() {
		// TODO Auto-generated method stub
		if (loanList.size() == LOAN_LIMIT) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasFinesPayable() {
		if (totalFines > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasReachedFineLimit() {
		// TODO Auto-generated method stub
		if (totalFines > FINE_LIMIT) {
			return true;
		}
		return false;
	}

	@Override
	public float getFineAmount() {
		// TODO Auto-generated method stub
		return totalFines;
	}

	@Override
	public void addFine(float amount) {
		// TODO Auto-generated method stub
		if (amount < 0) {
			throw new IllegalArgumentException("amount cannot be less than zero");
		} else {
			totalFines += amount;
		}
	}

	@Override
	public void payFine(float amount) {
		// TODO Auto-generated method stub
		if (amount < 0 || amount > totalFines) {
			throw new IllegalArgumentException("amount cannot be less than zero");
		} else {
			totalFines -= amount;
		}
	}

	@Override
	public void addLoan(ILoan loan) {
		// TODO Auto-generated method stub
		if (loan == null) {
			throw new IllegalArgumentException("loan cannot be null");
		} else if (state == EMemberState.BORROWING_DISALLOWED) {
			throw new IllegalArgumentException("this memeber cannot borrow");
		} else {
			loanList.add(loan);
		}
	}

	@Override
	public List<ILoan> getLoans() {
		// TODO Auto-generated method stub
		return (List<ILoan>) loanList;
	}

	@Override
	public void removeLoan(ILoan loan) {
		// TODO Auto-generated method stub
		if (loan == null || !loanList.contains(loan)) {
			throw new IllegalArgumentException("loan cannot be removed");
		} else {
			loanList.remove(loan);
		}
	}

	@Override
	public EMemberState getState() {
		// TODO Auto-generated method stub
		return state;
	}

	@Override
	public String getFirstName() {
		// TODO Auto-generated method stub
		return firstName;
	}

	@Override
	public String getLastName() {
		// TODO Auto-generated method stub
		return lastName;
	}

	@Override
	public String getContactPhone() {
		// TODO Auto-generated method stub
		return contactPhone;
	}

	@Override
	public String getEmailAddress() {
		// TODO Auto-generated method stub
		return emailAddress;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return id;
	}
}
