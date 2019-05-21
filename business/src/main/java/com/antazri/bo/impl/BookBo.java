package com.antazri.bo.impl;

import com.antazri.bo.contract.IBookBo;
import com.antazri.dao.contract.IBookDao;
import com.antazri.model.bean.Author;
import com.antazri.model.bean.Book;
import com.antazri.model.bean.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implémentation de l'interface {@link com.antazri.bo.contract.IBookBo} gérant les transactions entre le web service
 * et la couche DAO via le bean {@link com.antazri.dao.impl.BookDao}.
 */
@Component
public class BookBo implements IBookBo {

    @Autowired
    private IBookDao bookDao;

    /**
     * La méthode findById permet de récupérer une instance de {@link com.antazri.model.bean.Book} à l'aide de son
     * identifiant unique via l'objet BookDao injecté par Spring.
     * @param pId est un Integer représentant l'identifiant unique ID de l'objet
     * @return un objet (Hibernate) {@link com.antazri.model.bean.Book}
     */
    @Transactional(readOnly = true)
    @Override
    public Book findById(int pId) {
        return bookDao.findById(pId);
    }

    /**
     * La méthode findByTitle permet de récupérer une liste d'une ou plusieurs instances de
     * {@link com.antazri.model.bean.Book} via l'objet BookDao injecté par Spring.
     * @param pTitle est un String utilisé et comparé à l'attribut "title"
     * @return une List d'objets (Hibernate) {@link com.antazri.model.bean.Loan}
     */
    @Transactional(readOnly = true)
    @Override
    public List<Book> findByTitle(String pTitle) {
        return bookDao.findByTitle(pTitle);
    }

    /**
     * La méthode findByAuthor permet de récupérer une liste d'une ou plusieurs instances de
     * {@link com.antazri.model.bean.Book} liées à un {@link com.antazri.model.bean.Author} via
     * l'objet BookDao injecté par Spring.
     * @param pAuthor est un objet {@link com.antazri.model.bean.Book} permettant de retrouver les objets
     *                {@link com.antazri.model.bean.Loan} liés
     * @return une List d'objets (Hibernate) {@link com.antazri.model.bean.Loan}
     */
    @Transactional(readOnly = true)
    @Override
    public List<Book> findByAuthor(Author pAuthor) {
        return bookDao.findByAuthor(pAuthor);
    }

    /**
     * La méthode findByCategory permet de récupérer une liste d'une ou plusieurs instances de
     * {@link com.antazri.model.bean.Book} liées à un {@link com.antazri.model.bean.Category} via
     * l'objet BookDao injecté par Spring.
     * @param pCategory est un objet {@link com.antazri.model.bean.Book} permettant de retrouver les objets
     *                {@link com.antazri.model.bean.Loan} liés
     * @return une List d'objets (Hibernate) {@link com.antazri.model.bean.Loan}
     */
    @Transactional(readOnly = true)
    @Override
    public List<Book> findByCategory(Category pCategory) {
        return bookDao.findByCategory(pCategory);
    }

    /**
     * La méthode findAll permet de récupérer toutes les instances de {@link com.antazri.model.bean.Book}
     * de la base de données via l'objet BookDao injecté par Spring.
     * @return une List d'objets (Hibernate) {@link com.antazri.model.bean.Book}
     */
    @Transactional(readOnly = true)
    @Override
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    /**
     * La méthode add est utilisée pour enregistrer une nouvelle instance de {@link com.antazri.model.bean.Book} dans
     * la base de données via l'objet BookDao injecté par Spring
     * @param pBook est un objet {@link com.antazri.model.bean.Book} récupéré depuis le web service
     * @return l'objet {@link com.antazri.model.bean.Book} après sa sauvegarde dans la table
     */
    @Transactional
    @Override
    public Book add(Book pBook) {
        return bookDao.add(pBook);
    }

    /**
     * La méthode update est utilisée pour modifier une instance de {@link com.antazri.model.bean.Book} dans
     * la base de données via l'objet BookDao injecté par Spring
     * @param pBook est un objet {@link com.antazri.model.bean.Book} récupéré depuis le web service
     * @return l'objet {@link com.antazri.model.bean.Book} après sa mise à jour dans la table
     */
    @Transactional
    @Override
    public Book update(Book pBook) {
        return bookDao.update(pBook);
    }

    /**
     * La méthode delete est utilisée pour supprimer une instance de {@link com.antazri.model.bean.Book} dans
     * la base de données via l'objet BookDao injecté par Spring
     * @param pBook est un objet {@link com.antazri.model.bean.Book} récupéré depuis le web service
     */
    @Transactional
    @Override
    public void delete(Book pBook) {
        bookDao.delete(pBook);
    }
}
