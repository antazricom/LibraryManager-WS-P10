package com.antazri.dao.contract;

import com.antazri.model.bean.Book;
import com.antazri.model.bean.Loan;
import com.antazri.model.bean.Member;

import java.util.List;

public interface ILoanDao {

    Loan findById(int pId);
    List<Loan> findByMember(Member pMember);
    List<Loan> findByBook(Book pBook);
    int countLoansByBook(Book pBook);
    List<Loan> findAll();
    Loan add(Loan pLoan);
    Loan update(Loan pLoan);
    void delete(Loan pLoan);
}
