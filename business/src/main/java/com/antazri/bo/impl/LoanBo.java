package com.antazri.bo.impl;

import com.antazri.bo.contract.ILoanBo;
import com.antazri.bo.utils.contract.IDateExtension;
import com.antazri.bo.utils.impl.CheckEndDate;
import com.antazri.bo.utils.impl.DateExtension;
import com.antazri.dao.contract.ILoanDao;
import com.antazri.model.bean.Book;
import com.antazri.model.bean.Loan;
import com.antazri.model.bean.Member;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Implémentation de l'interface {@link com.antazri.bo.contract.ILoanBo} gérant les transactions entre le web service
 * et la couche DAO via le bean {@link com.antazri.dao.impl.LoanDao}.
 */
@Component
public class LoanBo implements ILoanBo {

    private Logger logger = LogManager.getLogger(LoanBo.class);
    private IDateExtension dateExtension;

    @Autowired
    private ILoanDao loanDao;

    /**
     * La méthode findById permet de récupérer une instance de {@link com.antazri.model.bean.Member} à l'aide de son
     * identifiant unique via l'objet loanDao injecté par Spring.
     * @param pId est un Integer représentant l'identifiant unique ID de l'objet
     * @return un objet (Hibernate) {@link com.antazri.model.bean.Loan}
     */
    @Override
    public Loan findById(int pId) {
        return loanDao.findById(pId);
    }

    /**
     * La méthode findByMember permet de récupérer une liste d'instances de {@link com.antazri.model.bean.Loan} liées à
     * un {@link com.antazri.model.bean.Member}  via l'objet loanDao injecté par Spring.
     * @param pMember est un objet {@link com.antazri.model.bean.Member} permettant de retrouver les objets
     *                {@link com.antazri.model.bean.Loan} liés
     * @return une List d'objets (Hibernate) {@link com.antazri.model.bean.Loan}
     */
    @Transactional(readOnly = true)
    @Override
    public List<Loan> findByMember(Member pMember) {
        return loanDao.findByMember(pMember);
    }

    /**
     * La méthode findByBook permet de récupérer une liste d'instances de {@link com.antazri.model.bean.Loan} liées à
     * un {@link com.antazri.model.bean.Book}  via l'objet loanDao injecté par Spring.
     * @param pBook est un objet {@link com.antazri.model.bean.Book} permettant de retrouver les objets
     *                {@link com.antazri.model.bean.Loan} liés
     * @return une List d'objets (Hibernate) {@link com.antazri.model.bean.Loan}
     */
    @Transactional(readOnly = true)
    @Override
    public List<Loan> findByBook(Book pBook) {
        return loanDao.findByBook(pBook);
    }

    /**
     * La méthode countLoansByBook permet de récupérer une liste d'instances de {@link com.antazri.model.bean.Loan}
     * liées un {@link com.antazri.model.bean.Book}  via l'objet loanDao injecté par Spring.
     * @param pBook est un objet {@link com.antazri.model.bean.Book} permettant de retrouver les objets
     *                {@link com.antazri.model.bean.Loan} liés
     * @return un Integer définissant le nombre d'occurences trouvées
     */
    @Transactional(readOnly = true)
    @Override
    public int countLoansByBook(Book pBook) {
        return loanDao.countLoansByBook(pBook);
    }

    /**
     * La méthode findLateLoan récupère l'ensemble des occurences de Loan dans la table "loan" de la base de données
     * puis vérifie pour chacune d'elle si l'attribut "returned" est à TRUE. Si c'est à FALSE, une seconde condition
     * dans la bouche FOR vérifie si l'attribut "DateEnd" est une date passée. Si la méthode renvoie TRUE, l'objet
     * {@link com.antazri.model.bean.Loan} est ajouté à une List retournée par la méthode.
     * @return une List d'objets {@link com.antazri.model.bean.Loan}
     */
    @Override
    public List<Loan> findLateLoan() {
        List<Loan> vAllLoans = findAll();
        List<Loan> vLateLoans = new ArrayList<>();
        CheckEndDate checkEndDate = new CheckEndDate();

        for (Loan loan : vAllLoans) {
            if (!loan.isReturned()) {
                if (checkEndDate.isDateEndPassed(loan.getDateEnd())) {
                    vLateLoans.add(loan);
                }
            }
        }

        return vLateLoans;
    }

    /**
     * La méthode findAll permet de récupérer toutes les instances de {@link com.antazri.model.bean.Loan}
     * de la base de données via l'objet loanDao injecté par Spring.
     * @return une List d'objets (Hibernate) {@link com.antazri.model.bean.Loan}
     */
    @Transactional(readOnly = true)
    @Override
    public List<Loan> findAll() {
        return loanDao.findAll();
    }

    /**
     * La méthode extendLoan utilise un {@link com.antazri.bo.utils.impl.DateExtension} pour modifier et appliquer les
     * attributs Extended, DateEnd et DateReturn de {@link com.antazri.model.bean.Loan}
     * @param pLoan est un objet {@link com.antazri.model.bean.Loan} récupéré depuis le web service
     * @return l'objet (Hibernate) {@link com.antazri.model.bean.Loan} modifié
     */
    @Transactional
    @Override
    public Loan extendLoan(Loan pLoan) {
        dateExtension = new DateExtension();
        pLoan.setDateEnd(dateExtension.extendEndDate(pLoan.getDateEnd()));
        pLoan.setDateReturn(dateExtension.extendEndDate(pLoan.getDateReturn()));
        pLoan.setExtended(true);

        return loanDao.update(pLoan);
    }

    /**
     * La méthode returnLoan utilise est utilisé pour modifier et appliquer l'attribut Returned, spécifiant qu'un
     * {@link com.antazri.model.bean.Loan} se termine
     * @param pLoan est un objet {@link com.antazri.model.bean.Loan} récupéré depuis le web service
     * @return l'objet (Hibernate) {@link com.antazri.model.bean.Loan} modifié
     */
    @Transactional
    @Override
    public Loan returnLoan(Loan pLoan) {
        pLoan.setDateReturn(Date.from(Instant.now()));
        pLoan.setReturned(true);

        return loanDao.update(pLoan);
    }

    /**
     * La méthode add est utilisée pour enregistrer une nouvelle instance de {@link com.antazri.model.bean.Loan} dans
     * la base de données via l'objet loanDao injecté par Spring
     * @param pLoan est un objet {@link com.antazri.model.bean.Loan} récupéré depuis le web service
     * @return l'objet {@link com.antazri.model.bean.Loan} après sa sauvegarde dans la table
     */
    @Transactional
    @Override
    public Loan add(Loan pLoan) {
        return loanDao.add(pLoan);
    }

    /**
     * La méthode update est utilisée pour modifier une instance de {@link com.antazri.model.bean.Loan} dans
     * la base de données via l'objet loanDao injecté par Spring
     * @param pLoan est un objet {@link com.antazri.model.bean.Loan} récupéré depuis le web service
     * @return l'objet {@link com.antazri.model.bean.Loan} après sa mise à jour dans la table
     */
    @Transactional
    @Override
    public Loan update(Loan pLoan) {
        return loanDao.update(pLoan);
    }

    /**
     * La méthode delete est utilisée pour supprimer une instance de {@link com.antazri.model.bean.Loan} dans
     * la base de données via l'objet loanDao injecté par Spring
     * @param pLoan est un objet {@link com.antazri.model.bean.Loan} récupéré depuis le web service
     */
    @Transactional
    @Override
    public void delete(Loan pLoan) {
        loanDao.delete(pLoan);
    }
}
