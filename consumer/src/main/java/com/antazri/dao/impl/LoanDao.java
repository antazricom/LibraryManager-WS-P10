package com.antazri.dao.impl;

import com.antazri.dao.contract.ILoanDao;
import com.antazri.model.bean.Book;
import com.antazri.model.bean.Loan;
import com.antazri.model.bean.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * La classe LoanDao est l'implémentation de l'interface {@link com.antazri.dao.contract.ILoanDao} gérant la
 * connexion et la relation avec la table "loan" de la base de données.
 */
@Repository
public class LoanDao implements ILoanDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * La méthode findById permet de retrouver une instance de {@link com.antazri.model.bean.Loan} à l'aide de
     * l'EntityManager et sa méthode find() utilisant le paramètre pId
     * @param pId est un Integer récupéré via la couche Business
     * @return une instance de {@link com.antazri.model.bean.Loan}
     */
    @Override
    public Loan findById(int pId) {
        return entityManager.find(Loan.class, pId);
    }

    /**
     * La méthode findByMember permet de retrouver l'ensemble des occurences de {@link com.antazri.model.bean.Loan}
     * liées à une instance de {@link com.antazri.model.bean.Member}. Une @NamedQuery permet de faire une jointure et
     * de filtrer selon l'identifiant unique de l'objet passé en paramètre
     * @param pMember est un objet {@link com.antazri.model.bean.Member} récupéré via la couche business
     * @return une List d'un ou plusieurs objets {@link com.antazri.model.bean.Loan}
     */
    @Override
    public List<Loan> findByMember(Member pMember) {
        Query vQuery = entityManager.createNamedQuery("Loan.findByMember");
        vQuery.setParameter("id", pMember.getId());
        return vQuery.getResultList();
    }

    /**
     * La méthode findByBook permet de retrouver l'ensemble des occurences de {@link com.antazri.model.bean.Loan}
     * liées à une instance de {@link com.antazri.model.bean.Book}. Une @NamedQuery permet de faire une jointure et
     * de filtrer selon l'identifiant unique de l'objet passé en paramètre
     * @param pBook est un objet {@link com.antazri.model.bean.Book} récupéré via la couche business
     * @return une List d'un ou plusieurs objets {@link com.antazri.model.bean.Loan}
     */
    @Override
    public List<Loan> findByBook(Book pBook) {
        Query vQuery = entityManager.createNamedQuery("Loan.findByBook");
        vQuery.setParameter("id", pBook.getId());
        return vQuery.getResultList();
    }

    /**
     * La méthode countLoansByBook permet de compter, via l'argument SQL "COUNT", le nombre d'occurence de
     * {@link com.antazri.model.bean.Loan} liées à une instance de {@link com.antazri.model.bean.Book}. Une @NamedQuery
     * permet de faire une jointure et de filtrer selon l'identifiant unique de l'objet passé en paramètre
     * @param pBook est un objet {@link com.antazri.model.bean.Member} récupéré via la couche business
     * @return une List d'un ou plusieurs objets {@link com.antazri.model.bean.Loan}
     */
    @Override
    public int countLoansByBook(Book pBook) {
        Query vQuery = entityManager.createNamedQuery("Loan.findByBook");
        vQuery.setParameter("id", pBook.getId());
        return vQuery.getMaxResults();
    }

    /**
     * La méthode findAll permet de retrouver toutes les occurences de {@link com.antazri.model.bean.Loan} de la
     * table "book". L'EntityManager utilise une @NamedQuery définie dans le Bean model (objet Hibernate)
     * @return une List d'objets {@link com.antazri.model.bean.Loan}
     */
    @Override
    public List<Loan> findAll() {
        Query vQuery = entityManager.createNamedQuery("Loan.findAll");
        return vQuery.getResultList();
    }

    /**
     * La méthode add permet d'enregistrer une nouvelle instance de {@link com.antazri.model.bean.Loan} dans la
     * table "loan". L'EntityManager utilise sa méthode persist() pour l'ajouter dans la base de données.
     * @param pLoan est un objet {@link com.antazri.model.bean.Loan} récupéré via la couche Business
     * @return l'objet {@link com.antazri.model.bean.Loan} passé en paramètre après avoir été persisté
     */
    @Override
    public Loan add(Loan pLoan) {
        entityManager.persist(entityManager.contains(pLoan) ? pLoan : entityManager.merge(pLoan));
        return pLoan;
    }

    /**
     * La méthode update permet de mettre à jour une instance de {@link com.antazri.model.bean.Loan} dans la
     * table "loan". L'EntityManager utilise sa méthode merge() pour mettre à jour la base de données.
     * @param pLoan est un objet {@link com.antazri.model.bean.Loan} récupéré via la couche Business
     * @return l'objet {@link com.antazri.model.bean.Loan} passé en paramètre après avoir été mergé
     */
    @Override
    public Loan update(Loan pLoan) {
        entityManager.merge(pLoan);
        return pLoan;
    }

    /**
     * La méthode delete permet de supprimer une instance de {@link com.antazri.model.bean.Loan} dans la
     * table "loan". L'EntityManager utilise sa méthode delete() pour l'ajouter dans la base de données.
     * Une condition ternaire va vérifier la présence de l'objet dans le contexte Hibernate (@PersistenceContext)
     * et le merger si ce n'est pas le cas pour effectuer la suppression
     * @param pLoan est un objet {@link com.antazri.model.bean.Loan} récupéré via la couche Business
     */
    @Override
    public void delete(Loan pLoan) {
        entityManager.remove(entityManager.contains(pLoan) ? pLoan : entityManager.merge(pLoan));
    }
}
