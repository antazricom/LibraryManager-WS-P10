package com.antazri.security.impl;

import com.antazri.model.bean.Fault;
import com.antazri.model.bean.Member;
import com.antazri.security.contract.IPasswordManagement;
import com.antazri.security.exception.BadLoginException;
import org.mindrot.jbcrypt.BCrypt;

/**
 * La classe PasswordManagement est utilisée pour gérer les mots de passe de la table "member".
 */
public class PasswordManagement implements IPasswordManagement {

    /**
     * La méthode isValidPassword vérifie la validité d'un mot de passe clair envoyé depuis le web service à travers
     * l'objet "MemberBo". Elle utilise la méthode checkpw de BCrypt.
     * @param pMember est un Objet {@link com.antazri.model.bean.Member} passé par la couche business et dont la
     * @param pPlainPassword est un String envoyé depuis la couche business
     * @return un booléen pour valider ou non la vérification
     * @throws BadLoginException
     */
    @Override
    public boolean isValidPassword(Member pMember, String pPlainPassword) throws BadLoginException {

        if (!BCrypt.checkpw(pPlainPassword, pMember.getPassword())) {
            throw new BadLoginException("error.message.badlogin", new Fault("403", "error.message.badlogin"));
        }

        return true;
    }

    /**
     * La méthode hasPassword permet de hasher un mot de passe clair à l'aide de BCrypt et de sa méthode haspw
     * @param pPassword est un le mot de passe en clair (String) passé par la couche business
     * @return un String de 60 caractères correspondant au mot de passe hashé
     */
    @Override
    public String hashPassword(String pPassword) {
        return BCrypt.hashpw(pPassword, BCrypt.gensalt());
    }
}
