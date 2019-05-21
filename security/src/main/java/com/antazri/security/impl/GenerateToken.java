package com.antazri.security.impl;

import com.antazri.model.bean.Token;
import com.antazri.security.contract.IGenerateToken;
import org.mindrot.jbcrypt.BCrypt;

/**
 * La classe GenerateToken est l'implémentation de l'interface fonctionnelle {@link com.antazri.security.contract.IGenerateToken}.
 * Elle est utilisée pour généré un {@link com.antazri.model.bean.Token} permettant de vérifier la connexion d'un
 * member au web service
 */
public class GenerateToken implements IGenerateToken {

    /**
     * La méthode generate va utiliser la chaine de caractères en paramètre afin de générer une clé de 60 caractères
     * qui sera stockée dans la session de l'utilisateur via l'AuthenticationManager si la connexion est vérifiée
     * @param pLogin est un String envoyé depuis l'AuthenticationManager
     * @return un objet {@link com.antazri.model.bean.Token}
     * @see com.antazri.security.impl.AuthenticateManager
     */
    @Override
    public Token generate(String pLogin) {
        Token vToken = new Token(BCrypt.hashpw(pLogin, BCrypt.gensalt()));
        return vToken;
    }
}
