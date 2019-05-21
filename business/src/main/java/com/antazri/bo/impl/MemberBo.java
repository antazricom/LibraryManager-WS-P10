package com.antazri.bo.impl;

import com.antazri.bo.contract.IMemberBo;
import com.antazri.bo.exception.BadLoginException;
import com.antazri.dao.contract.IMemberDao;
import com.antazri.model.bean.Fault;
import com.antazri.model.bean.Member;
import com.antazri.model.bean.Token;
import com.antazri.security.contract.IAuthenticateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implémentation de l'interface {@link com.antazri.bo.contract.IMemberBo} gérant les transactions entre le web
 * service et la couche DAO avec le bean {@link com.antazri.dao.impl.MemberDao}. La classe MemberBo fait également
 * appel au module Security pour gérer la connexion des utilisateurs à l'application.
 */
@Component
public class MemberBo implements IMemberBo {

    @Autowired
    private IMemberDao memberDao;

    @Autowired
    private IAuthenticateManager authenticateManager;

    /**
     * La méthode findById permet de récupérer une instance de {@link com.antazri.model.bean.Member} à l'aide de son identifiant unique via l'objet
     * memberDao injecté par Spring.
     * @param pId est un Integer représentant l'identifiant unique ID de l'objet
     * @return un objet (Hibernate) {@link com.antazri.model.bean.Member}
     */
    @Transactional(readOnly = true)
    @Override
    public Member findById(int pId) {
        return memberDao.findById(pId);
    }

    /**
     * La méthode findByLogin permet de récupérer une instance de {@link com.antazri.model.bean.Member} à l'aide de son attribut "login" via l'objet
     * memberDao injecté par Spring.
     * @param pLogin est un String utilisé pour rechercher une instance de Member via son attribut "login"
     * @return un objet (Hibernate) {@link com.antazri.model.bean.Member}
     */
    @Transactional(readOnly = true)
    @Override
    public Member findByLogin(String pLogin) throws BadLoginException {
        Member vMember = memberDao.findByLogin(pLogin);

        if (vMember == null) {
            throw new BadLoginException("error.message.badlogin", new Fault("finByLogin: bad Login parameter", "error.message.badlogin" + " : " + this.getClass().getName()));
        }

        return vMember;
    }

    /**
     * La méthode findAll permet de récupérer l'ensemble des instances de {@link com.antazri.model.bean.Member} de la base de données unique via
     * l'objet memberDao injecté par Spring.
     * @return une List d'objets (Hibernate) {@link com.antazri.model.bean.Member}
     */
    @Transactional
    @Override
    public List<Member> findAll() {
        return memberDao.findAll();
    }

    /**
     * La méthode add permet d'enregistrer une nouvelle occurence de {@link com.antazri.model.bean.Member} dans la base de données via l'objet
     * member Dao injecté par Spring
     * @param pMember est un objet (Hibernate) {@link com.antazri.model.bean.Member} récupéré depuis le Web Service
     * @return l'objet {@link com.antazri.model.bean.Member} envoyé après son enregistrement dans la base de données par l'EntityManager
     */
    @Transactional
    @Override
    public Member add(Member pMember) {
        return memberDao.add(pMember);
    }

    /**
     * La méthode add permet de mettre à jour une occurence de Member dans la base de données via l'objet
     * member Dao injecté par Spring
     * @param pMember est un objet (Hibernate) {@link com.antazri.model.bean.Member} récupéré depuis le Web Service
     * @return l'objet {@link com.antazri.model.bean.Member} envoyé après sa modification dans la base de données par l'EntityManager
     */
    @Transactional
    @Override
    public Member update(Member pMember) {
        return memberDao.update(pMember);
    }

    /**
     * La méthode add permet de supprimer une occurence de {@link com.antazri.model.bean.Member} dans la base de données via l'objet
     * member Dao injecté par Spring
     * @param pMember est un objet (Hibernate) {@link com.antazri.model.bean.Member} récupéré depuis le Web Service
     */
    @Transactional
    @Override
    public void delete(Member pMember) {
        memberDao.delete(pMember);
    }

    /**
     * La méthode login permet de gérer la connexion d'un utilisateur (via le service Authentication du module Web
     * Service). Elle utilise la classe {@link com.antazri.security.impl.AuthenticateManager} pour vérifier le
     * mot de passe clair en paramètre et le mot de passe hashé en base de données.
     * @param pMember est un objet (Hibernate) {@link com.antazri.model.bean.Member} récupéré depuis le Web Service
     * @param pPassword est un String correspondant au mot de passe envoyé en clair depuis le WebService
     * @return un {@link com.antazri.model.bean.Token} permettant la confirmation de l'identification
     * @throws BadLoginException
     */
    @Transactional
    @Override
    public Token login(Member pMember, String pPassword) throws BadLoginException {
        Token vToken;

        try {
            vToken = authenticateManager.login(pMember, pPassword);
        } catch (com.antazri.security.exception.BadLoginException pE) {
            throw new BadLoginException(pE.getMessage(), new Fault("403", "error.message.badlogin"));
        }

        return vToken;
    }
}
