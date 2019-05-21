package com.antazri.bo.contract;

import com.antazri.bo.exception.BadLoginException;
import com.antazri.model.bean.Member;
import com.antazri.model.bean.Token;

import java.util.List;

public interface IMemberBo {

    Member findById(int pId);
    Member findByLogin(String pLogin) throws BadLoginException;
    List<Member> findAll();
    Member add(Member pMember);
    Member update(Member pMember);
    void delete(Member pMember);
    Token login(Member pMember, String pPassword) throws BadLoginException;
}
