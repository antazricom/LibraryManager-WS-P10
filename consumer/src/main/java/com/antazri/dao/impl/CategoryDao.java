package com.antazri.dao.impl;

import com.antazri.dao.contract.ICategoryDao;
import com.antazri.model.bean.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * La classe CategoryDao est l'implémentation de l'interface {@link com.antazri.dao.contract.ICategoryDao} gérant la
 * connexion et la relation avec la table "category" de la base de données.
 */
@Repository
public class CategoryDao implements ICategoryDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * La méthode findById permet de retrouver une instance de {@link com.antazri.model.bean.Category} à l'aide de
     * l'EntityManager et sa méthode find() utilisant le paramètre pId
     * @param pId est un Integer récupéré via la couche Business
     * @return une instance de {@link com.antazri.model.bean.Category}
     */
    @Override
    public Category findById(int pId) {
        return entityManager.find(Category.class, pId);
    }

    /**
     * La méthode findAll permet de retrouver toutes les occurences de {@link com.antazri.model.bean.Category} de la
     * table "category". L'EntityManager utilise une @NamedQuery définie dans le Bean model (objet Hibernate)
     * @return une List d'objets {@link com.antazri.model.bean.Category}
     */
    @Override
    public List<Category> findAll() {
        Query vQuery = entityManager.createNamedQuery("Category.findAll");
        return vQuery.getResultList();
    }

    /**
     * La méthode add permet d'enregistrer une nouvelle instance de {@link com.antazri.model.bean.Category} dans la
     * table "category". L'EntityManager utilise sa méthode persist() pour l'ajouter dans la base de données.
     * @param pCategory est un objet {@link com.antazri.model.bean.Category} récupéré via la couche Business
     * @return l'objet {@link com.antazri.model.bean.Category} passé en paramètre après avoir été persisté
     */
    @Override
    public Category add(Category pCategory) {
        entityManager.persist(pCategory);
        return pCategory;
    }

    /**
     * La méthode update permet de mettre à jour une instance de {@link com.antazri.model.bean.Category} dans la
     * table "category". L'EntityManager utilise sa méthode merge() pour mettre à jour la base de données.
     * @param pCategory est un objet {@link com.antazri.model.bean.Category} récupéré via la couche Business
     * @return l'objet {@link com.antazri.model.bean.Category} passé en paramètre après avoir été mergé
     */
    @Override
    public Category update(Category pCategory) {
        entityManager.merge(pCategory);
        return pCategory;
    }

    /**
     * La méthode delete permet de supprimer une instance de {@link com.antazri.model.bean.Category} dans la
     * table "category". L'EntityManager utilise sa méthode delete() pour l'ajouter dans la base de données.
     * Une condition ternaire va vérifier la présence de l'objet dans le contexte Hibernate (@PersistenceContext)
     * et le merger si ce n'est pas le cas pour effectuer la suppression
     * @param pCategory est un objet {@link com.antazri.model.bean.Category} récupéré via la couche Business
     */
    @Override
    public void delete(Category pCategory) {
        entityManager.remove(entityManager.contains(pCategory) ? pCategory : entityManager.merge(pCategory));
    }
}
