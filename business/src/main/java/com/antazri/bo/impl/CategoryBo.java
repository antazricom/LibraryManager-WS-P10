package com.antazri.bo.impl;

import com.antazri.bo.contract.ICategoryBo;
import com.antazri.dao.contract.ICategoryDao;
import com.antazri.model.bean.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implémentation de l'interface {@link com.antazri.bo.contract.ICategoryBo} gérant les transactions entre le web service
 * et la couche DAO via le bean {@link com.antazri.dao.impl.CategoryDao}.
 */
@Component
public class CategoryBo implements ICategoryBo {

    @Autowired
    private ICategoryDao categoryDao;

    /**
     * La méthode findById permet de récupérer une instance de {@link com.antazri.model.bean.Category} à l'aide de son
     * identifiant unique via l'objet categoryDao injecté par Spring.
     * @param pId est un Integer représentant l'identifiant unique ID de l'objet
     * @return un objet (Hibernate) {@link com.antazri.model.bean.Category}
     */
    @Transactional(readOnly = true)
    @Override
    public Category findById(int pId) {
        return categoryDao.findById(pId);
    }

    /**
     * La méthode findAll permet de récupérer toutes les instances de {@link com.antazri.model.bean.Category}
     * de la base de données via l'objet categoryDao injecté par Spring.
     * @return une List d'objets (Hibernate) {@link com.antazri.model.bean.Category}
     */
    @Transactional(readOnly = true)
    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    /**
     * La méthode add est utilisée pour enregistrer une nouvelle instance de {@link com.antazri.model.bean.Category} dans
     * la base de données via l'objet categoryDao injecté par Spring
     * @param pCategory est un objet {@link com.antazri.model.bean.Category} récupéré depuis le web service
     * @return l'objet {@link com.antazri.model.bean.Category} après sa sauvegarde dans la table
     */
    @Transactional
    @Override
    public Category add(Category pCategory) {
        return categoryDao.add(pCategory);
    }

    /**
     * La méthode update est utilisée pour modifier une instance de {@link com.antazri.model.bean.Category} dans
     * la base de données via l'objet categoryDao injecté par Spring
     * @param pCategory est un objet {@link com.antazri.model.bean.Category} récupéré depuis le web service
     * @return l'objet {@link com.antazri.model.bean.Category} après sa mise à jour dans la table
     */
    @Transactional
    @Override
    public Category update(Category pCategory) {
        return categoryDao.update(pCategory);
    }

    /**
     * La méthode delete est utilisée pour supprimer une instance de {@link com.antazri.model.bean.Category} dans
     * la base de données via l'objet categoryDao injecté par Spring
     * @param pCategory est un objet {@link com.antazri.model.bean.Category} récupéré depuis le web service
     */
    @Transactional
    @Override
    public void delete(Category pCategory) {
        categoryDao.delete(pCategory);
    }
}
