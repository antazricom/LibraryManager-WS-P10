package com.antazri.dao.impl;

import com.antazri.dao.contract.IMemberDao;
import com.antazri.model.bean.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 *
 */
@Repository
public class MemberDao implements IMemberDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Member findById(int pId) {
        return entityManager.find(Member.class, pId);
    }

    @Override
    public Member findByLogin(String pLogin) {
        Query vQuery = entityManager.createNamedQuery("Member.findByLogin", Member.class);
        vQuery.setParameter("login", pLogin);
        return (Member) vQuery.getSingleResult();
    }

    @Override
    public List<Member> findAll() {
        Query vQuery = entityManager.createNamedQuery("Member.findAll");
        return vQuery.getResultList();
    }

    @Override
    public Member add(Member pMember) {
        entityManager.persist(pMember);
        return pMember;
    }

    @Override
    public Member update(Member pMember) {
        entityManager.merge(pMember);
        return pMember;
    }

    @Override
    public void delete(Member pMember) {
        entityManager.remove(entityManager.contains(pMember) ? pMember : entityManager.merge(pMember));
    }
}
