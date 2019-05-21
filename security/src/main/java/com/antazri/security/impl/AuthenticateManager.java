package com.antazri.security.impl;

import com.antazri.model.bean.Fault;
import com.antazri.model.bean.Member;
import com.antazri.model.bean.Token;
import com.antazri.model.utils.ErrorMessage;
import com.antazri.security.contract.IAuthenticateManager;
import com.antazri.security.contract.IGenerateToken;
import com.antazri.security.contract.IPasswordManagement;
import com.antazri.security.exception.BadLoginException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * AuthenticationManager est l'implémentation de l'interface {@link com.antazri.security.contract.IAuthenticateManager}
 * et permet de gérer l'authentification de l'utilisateur via le service Authentication
 */
public class AuthenticateManager implements IAuthenticateManager {

    @Autowired
    private IPasswordManagement passwordManagement;

    @Autowired
    private IGenerateToken generateToken;

    /**
     * La méthode utilise la classe passwordManagement pour vérifier le mot de passe passé en paramètre, selon le
     * Membre également en paramètre. Elle utilise la classe {@link com.antazri.security.impl.GenerateToken} pour
     * générer un {@link com.antazri.model.bean.Token}.
     * @param pMember est un objet {@link com.antazri.model.bean.Token} récupérer via la bouche business
     * @param pPassword est un String récupéré via la couche business et comparé au mot de passe hashé issu de la
     *                  base de données
     * @return un {@link com.antazri.model.bean.Token} généré via la classe {@link com.antazri.security.impl.GenerateToken}
     * @throws BadLoginException
     */
    @Override
    public Token login(Member pMember, String pPassword) throws BadLoginException {

        if (pMember == null) {
            throw new NullPointerException(ErrorMessage.getErrorMessages().getString("error.message.object.null"));
        }

        if (!passwordManagement.isValidPassword(pMember, pPassword)) {
            throw new BadLoginException("error.message.badlogin", new Fault("403", "error.message.badlogin"));
        }

        return generateToken.generate(pMember.getLogin());
    }
}
