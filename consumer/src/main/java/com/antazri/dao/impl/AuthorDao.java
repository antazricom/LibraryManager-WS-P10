package com.antazri.dao.impl;

import com.antazri.dao.contract.IAuthorDao;
import com.antazri.model.bean.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * La classe AuthorDao est l'implémentation de l'interface {@link com.antazri.dao.contract.IAuthorDao} gérant la
 * connexion et la relation avec la table "author" de la base de données.
 */
@Repository
public class AuthorDao implements IAuthorDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * La méthode findById permet de retrouver une instance de {@link com.antazri.model.bean.Author} à l'aide de
     * l'EntityManager et sa méthode find() utilisant le paramètre pId
     * @param pId est un Integer récupéré via la couche Business
     * @return une instance de {@link com.antazri.model.bean.Author}
     */
    @Override
    public Author findById(int pId) {
        return entityManager.find(Author.class, pId);
    }

    /**
     * La méthode findAll permet de retrouver toutes les occurences de {@link com.antazri.model.bean.Author} de la
     * table "author". L'EntityManager utilise une @NamedQuery définie dans le Bean model (objet Hibernate)
     * @return une List d'objets {@link com.antazri.model.bean.Author}
     */
    @Override
    public List<Author> findAll() {
        Query vQuery = entityManager.createNamedQuery("Author.findAll");
        return vQuery.getResultList();
    }

    /**
     * La méthode add permet d'enregistrer une nouvelle instance de {@link com.antazri.model.bean.Author} dans la
     * table "author". L'EntityManager utilise sa méthode persist() pour l'ajouter dans la base de données.
     * @param pAuthor est un objet {@link com.antazri.model.bean.Author} récupéré via la couche Business
     * @return l'objet {@link com.antazri.model.bean.Author} passé en paramètre après avoir été persisté
     */
    @Override
    public Author add(Author pAuthor) {
        entityManager.persist(pAuthor);
        return pAuthor;
    }

    /**
     * La méthode update permet de mettre à jour une instance de {@link com.antazri.model.bean.Author} dans la
     * table "author". L'EntityManager utilise sa méthode merge() pour mettre à jour la base de données.
     * @param pAuthor est un objet {@link com.antazri.model.bean.Author} récupéré via la couche Business
     * @return l'objet {@link com.antazri.model.bean.Author} passé en paramètre après avoir été mergé
     */
    @Override
    public Author update(Author pAuthor) {
        entityManager.merge(pAuthor);
        return pAuthor;
    }

    /**
     * La méthode delete permet de supprimer une instance de {@link com.antazri.model.bean.Author} dans la
     * table "author". L'EntityManager utilise sa méthode delete() pour l'ajouter dans la base de données.
     * Une condition ternaire va vérifier la présence de l'objet dans le contexte Hibernate (@PersistenceContext)
     * et le merger si ce n'est pas le cas pour effectuer la suppression
     * @param pAuthor est un objet {@link com.antazri.model.bean.Author} récupéré via la couche Business
     */
    @Override
    public void delete(Author pAuthor) {
        entityManager.remove(entityManager.contains(pAuthor) ? pAuthor : entityManager.merge(pAuthor));
    }
}
