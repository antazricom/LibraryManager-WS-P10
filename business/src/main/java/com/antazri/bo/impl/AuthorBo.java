package com.antazri.bo.impl;

import com.antazri.bo.contract.IAuthorBo;
import com.antazri.dao.contract.IAuthorDao;
import com.antazri.model.bean.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implémentation de l'interface {@link com.antazri.bo.contract.IAuthorBo} gérant les transactions entre le web service
 * et la couche DAO via le bean {@link com.antazri.dao.impl.AuthorDao}.
 */
@Component
public class AuthorBo implements IAuthorBo {

    @Autowired
    private IAuthorDao authorDao;

    /**
     * La méthode findById permet de récupérer une instance de {@link com.antazri.model.bean.Author} à l'aide de son
     * identifiant unique via l'objet AuthorDao injecté par Spring.
     * @param pId est un Integer représentant l'identifiant unique ID de l'objet
     * @return un objet (Hibernate) {@link com.antazri.model.bean.Author}
     */
    @Transactional(readOnly = true)
    @Override
    public Author findById(int pId) {
        return authorDao.findById(pId);
    }

    /**
     * La méthode findAll permet de récupérer toutes les instances de {@link com.antazri.model.bean.Author}
     * de la base de données via l'objet AuthorDao injecté par Spring.
     * @return une List d'objets (Hibernate) {@link com.antazri.model.bean.Author}
     */
    @Transactional(readOnly = true)
    @Override
    public List<Author> findAll() {
        return authorDao.findAll();
    }

    /**
     * La méthode add est utilisée pour enregistrer une nouvelle instance de {@link com.antazri.model.bean.Author} dans
     * la base de données via l'objet AuthorDao injecté par Spring
     * @param pAuthor est un objet {@link com.antazri.model.bean.Author} récupéré depuis le web service
     * @return l'objet {@link com.antazri.model.bean.Author} après sa sauvegarde dans la table
     */
    @Transactional
    @Override
    public Author add(Author pAuthor) {
        return authorDao.add(pAuthor);
    }

    /**
     * La méthode update est utilisée pour modifier une instance de {@link com.antazri.model.bean.Author} dans
     * la base de données via l'objet AuthorDao injecté par Spring
     * @param pAuthor est un objet {@link com.antazri.model.bean.Author} récupéré depuis le web service
     * @return l'objet {@link com.antazri.model.bean.Author} après sa mise à jour dans la table
     */
    @Transactional
    @Override
    public Author update(Author pAuthor) {
        return authorDao.update(pAuthor);
    }

    /**
     * La méthode delete est utilisée pour supprimer une instance de {@link com.antazri.model.bean.Author} dans
     * la base de données via l'objet AuthorDao injecté par Spring
     * @param pAuthor est un objet {@link com.antazri.model.bean.Author} récupéré depuis le web service
     */
    @Transactional
    @Override
    public void delete(Author pAuthor) {
        authorDao.delete(pAuthor);
    }
}
