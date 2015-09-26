package library.daos;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.ILoanHelper;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

public class LoanDAO implements ILoanDAO {
	
	int nextID = 0;
	Map<Integer, ILoan> loanMap;
	Calendar cal = Calendar.getInstance();;
	ILoanHelper helper;
	
	public LoanDAO (ILoanHelper helper) {
		if (helper == null) {
			throw new IllegalArgumentException("helper cannot be null");
		} else {
			this.helper = helper;
		}
	}

	@Override
	public ILoan createLoan(IMember borrower, IBook book) {
		// TODO Auto-generated method stub
		Date borrowDate = cal.getTime();
		cal.setTime(borrowDate);
		cal.add(Calendar.DATE, ILoan.LOAN_PERIOD);
		Date dueDate = cal.getTime();
				
		ILoan newLoan = helper.makeLoan(book, borrower, borrowDate, dueDate);
		if (borrower == null || book == null) {
			throw new IllegalArgumentException("borrower and book cannot be null");
		}
		return newLoan;
		
	}

	@Override
	public void commitLoan(ILoan loan) {
		// TODO Auto-generated method stub
		loanMap.put(nextID, loan);
		nextID++;
	}

	@Override
	public ILoan getLoanByID(int id) {
		// TODO Auto-generated method stub
		ILoan loan = loanMap.get(id);
		if (loan == null) {
			return null;
		}
		return loan;
	}

	@Override
	public List<ILoan> listLoans() {
		// TODO Auto-generated method stub
		List <ILoan> loanList = null;
		for (int i = 0; i < loanMap.size(); i++) {
			loanList.add(loanMap.get(i));
		}
		return loanList;
		
	}

	@Override
	public List<ILoan> findLoansByBorrower(IMember borrower) {
		// TODO Auto-generated method stub
		List <ILoan> loanList = null;
		for (int i = 0; i < loanMap.size(); i++) {
			ILoan loan = loanMap.get(i);
			if (loan.getBorrower() == borrower) {
				loanList.add(loan);
			}
		}
		return loanList;
		
	}

	@Override
	public List<ILoan> findLoansByBookTitle(String title) {
		// TODO Auto-generated method stub
		List <ILoan> loanList = null;
		for (int i = 0; i < loanMap.size(); i++) {
			ILoan loan = loanMap.get(i);
			if (loan.getBook().getTitle() == title) {
				loanList.add(loan);
			}
		}
		return loanList;
	
	}

	@Override
	public void updateOverDueStatus(Date currentDate) {
		// TODO Auto-generated method stub
		for (int i = 0; i < loanMap.size(); i++) {
			ILoan loan = loanMap.get(i);
			if (loan.isCurrent()) {
				boolean overDue = loan.checkOverDue(currentDate);
			}
			
		}
	}

	@Override
	public List<ILoan> findOverDueLoans() {
		// TODO Auto-generated method stub
		List <ILoan> loanList = null;
		for (int i = 0; i < loanMap.size(); i++) {
			ILoan loan = loanMap.get(i);
			if (loan.isOverDue()) {
				loanList.add(loan);
			}
		}
		return loanList;
	}

}
