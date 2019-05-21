package com.antazri.dao.contract;

import com.antazri.model.bean.Member;

import java.util.List;

public interface IMemberDao {

    Member findById(int pId);
    Member findByLogin(String pLogin);
    List<Member> findAll();
    Member add(Member pMember);
    Member update(Member pMember);
    void delete(Member pMember);
}
