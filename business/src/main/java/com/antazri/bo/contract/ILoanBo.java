package com.antazri.bo.contract;

import com.antazri.model.bean.Member;
import com.antazri.model.bean.Book;
import com.antazri.model.bean.Loan;

import java.util.List;

public interface ILoanBo {

    Loan findById(int pId);
    List<Loan> findByMember(Member pMember);
    List<Loan> findByBook(Book pBook);
    int countLoansByBook(Book pBook);
    Loan extendLoan(Loan pLoan, int pExtension);
    int getMaximumExtension();
    Loan returnLoan(Loan pLoan);
    List<Loan> findAll();
    List<Loan> findLateLoan();
    Loan add(Loan pLoan);
    Loan update(Loan pLoan);
    void delete(Loan pLoan);
    List<Loan> findRunningLoansByBook(Book pBook);
}
