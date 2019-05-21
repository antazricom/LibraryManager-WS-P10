package com.antazri.dao.impl;

import com.antazri.dao.contract.IBookDao;
import com.antazri.model.bean.Author;
import com.antazri.model.bean.Book;
import com.antazri.model.bean.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * La classe BookDao est l'implémentation de l'interface {@link com.antazri.dao.contract.IBookDao} gérant la
 * connexion et la relation avec la table "book" de la base de données.
 */
@Repository
public class BookDao implements IBookDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * La méthode findById permet de retrouver une instance de {@link com.antazri.model.bean.Book} à l'aide de
     * l'EntityManager et sa méthode find() utilisant le paramètre pId
     * @param pId est un Integer récupéré via la couche Business
     * @return une instance de {@link com.antazri.model.bean.Book}
     */
    @Override
    public Book findById(int pId) {
        return entityManager.find(Book.class, pId);
    }

    /**
     * La méthode findByTitle permet de récupérer une ou plusieurs instances de {@link com.antazri.model.bean.Book}
     * comprenant le paramètre dans leur attribut "title". La requête SQL ajoute des modulo "%" et le paramètre
     * LIKE pour récupérer des occurences comprenant la chaîne de caractères spécifiée. L'EntityManager utilise une
     * @NamedQuery définie dans le Bean model (objet Hibernate)
     * @param pTitle est un String envoyé via la couche business et utilisé dans la requête SQL
     * @return une List d'un ou plusieurs objets {@link com.antazri.model.bean.Book}
     */
    @Override
    public List<Book> findByTitle(String pTitle) {
        Query vQuery = entityManager.createNamedQuery("Book.findByTitle");
        vQuery.setParameter("title", "%" + pTitle.toLowerCase() + "%");
        return vQuery.getResultList();
    }

    /**
     * La méthode findByAuthor permet de récupérer une ou plusieurs instances de {@link com.antazri.model.bean.Book}
     * comprenant le paramètre dans leur attribut "author". Hibernate va comparer l'identifiant unique de l'objet
     * en paramètre, utilisé dans la variable de la requête HQL, et celui de la @JoinColumn configurée
     * dans la table "book". L'EntityManager utilise une @NamedQuery définie dans le Bean model (objet Hibernate)
     * @param pAuthor est un objet {@link com.antazri.model.bean.Author} récupéré depuis la couche business
     * @return une List d'un ou plusieurs objets {@link com.antazri.model.bean.Book}
     */
    @Override
    public List<Book> findByAuthor(Author pAuthor) {
        Query vQuery = entityManager.createNamedQuery("Book.findByAuthor");
        vQuery.setParameter("id", pAuthor.getId());
        return vQuery.getResultList();
    }

    /**
     * La méthode findByAuthor permet de récupérer une ou plusieurs instances de {@link com.antazri.model.bean.Book}
     * comprenant le paramètre dans leur attribut "category". Hibernate va comparer l'identifiant unique de l'objet
     * en paramètre, utilisé dans la variable de la requête HQL, et celui de la @JoinColumn configurée
     * dans la table "book". L'EntityManager utilise une @NamedQuery définie dans le Bean model (objet Hibernate)
     * @param pCategory est un objet {@link com.antazri.model.bean.Category} récupéré depuis la couche business
     * @return une List d'un ou plusieurs objets {@link com.antazri.model.bean.Book}
     */
    @Override
    public List<Book> findByCategory(Category pCategory) {
        Query vQuery = entityManager.createNamedQuery("Book.findByCategory");
        vQuery.setParameter("id", pCategory.getId());
        return vQuery.getResultList();
    }

    /**
     * La méthode findAll permet de retrouver toutes les occurences de {@link com.antazri.model.bean.Book} de la
     * table "book". L'EntityManager utilise une @NamedQuery définie dans le Bean model (objet Hibernate)
     * @return une List d'objets {@link com.antazri.model.bean.Book}
     */
    @Override
    public List<Book> findAll() {
        Query vQuery = entityManager.createNamedQuery("Book.findAll");
        return vQuery.getResultList();
    }

    /**
     * La méthode add permet d'enregistrer une nouvelle instance de {@link com.antazri.model.bean.Book} dans la
     * table "book". L'EntityManager utilise sa méthode persist() pour l'ajouter dans la base de données.
     * @param pBook est un objet {@link com.antazri.model.bean.Book} récupéré via la couche Business
     * @return l'objet {@link com.antazri.model.bean.Book} passé en paramètre après avoir été persisté
     */
    @Override
    public Book add(Book pBook) {
        entityManager.persist(entityManager.contains(pBook) ? pBook : entityManager.merge(pBook));
        return pBook;
    }

    /**
     * La méthode update permet de mettre à jour une instance de {@link com.antazri.model.bean.Book} dans la
     * table "book". L'EntityManager utilise sa méthode merge() pour mettre à jour la base de données.
     * @param pBook est un objet {@link com.antazri.model.bean.Book} récupéré via la couche Business
     * @return l'objet {@link com.antazri.model.bean.Book} passé en paramètre après avoir été mergé
     */
    @Override
    public Book update(Book pBook) {
        entityManager.merge(pBook);
        return pBook;
    }

    /**
     * La méthode delete permet de supprimer une instance de {@link com.antazri.model.bean.Book} dans la
     * table "book". L'EntityManager utilise sa méthode delete() pour l'ajouter dans la base de données.
     * Une condition ternaire va vérifier la présence de l'objet dans le contexte Hibernate (@PersistenceContext)
     * et le merger si ce n'est pas le cas pour effectuer la suppression
     * @param pBook est un objet {@link com.antazri.model.bean.Book} récupéré via la couche Business
     */
    @Override
    public void delete(Book pBook) {
        entityManager.remove(entityManager.contains(pBook) ? pBook : entityManager.merge(pBook));
    }
}
