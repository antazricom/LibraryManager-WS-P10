package com.antazri.service.impl;

import com.antazri.bo.contract.IMemberBo;
import com.antazri.exception.ConvertException;
import com.antazri.generated.auth.*;
import com.antazri.mapper.contract.IGenericConverter;
import com.antazri.mapper.impl.GenericConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * La classe Authentication est l'implémentation du service Authentication/AuthPort généré par Apache CXF via le
 * fichier WSDL authentication.wsdl. Elle utilise le Logger fourni par Log4J2 pour le logging ainsi que le
 * mapper générique {@link com.antazri.mapper.impl.GenericConverter}
 */
@Service
public class Authentication implements AuthPort {

    private static final Logger logger = LogManager.getLogger(Authentication.class);

    @Autowired
    private IMemberBo memberBo;

    IGenericConverter<com.antazri.model.bean.Member, Member> vMemberConverterForWebService = new GenericConverter<>();
    IGenericConverter<com.antazri.model.bean.Token, Token> vTokenConverterForWebService = new GenericConverter<>();

    /**
     * La méthode doLogin permet de gérer la connexion des utilisateurs en utilisant les objets DoLoginRequest et
     * DoLoginResponse générés depuis le fichier WSDL.
     * @param parameters est un objet DoLoginRequest comprenant l'ensemble des propriétés nécessaires à l'opération
     *                   définie dans le WSDL
     * @return un objet {@link com.antazri.generated.auth.DoLoginResponse} comprenant l'ensemble des propriétés
     * retournées par l'opération du web service
     * @throws BadLoginException
     */
    @Override
    public DoLoginResponse doLogin(DoLoginRequest parameters) throws BadLoginException {
        DoLoginResponse vDoLoginResponse = new DoLoginResponse();
        com.antazri.model.bean.Member vMemberDatabase;
        com.antazri.model.bean.Token vTokenDatabase;

        try {
            vMemberDatabase = memberBo.findByLogin(parameters.getLogin());
            vTokenDatabase = memberBo.login(vMemberDatabase, parameters.getPassword());
        } catch (com.antazri.bo.exception.BadLoginException pE) {
            logger.error("error.message.badlogin");
            throw new BadLoginException(pE.getMessage(), setExceptionFault(pE));
        }

        try {
            vDoLoginResponse.setMember(vMemberConverterForWebService.toConvert(vMemberDatabase, new Member()));
            vDoLoginResponse.setToken(vTokenConverterForWebService.toConvert(vTokenDatabase, new Token()));
        } catch (ConvertException pE) {
            logger.error("error.message.mapper.convert");
            throw new BadLoginException(pE.getMessage());
        }

        return vDoLoginResponse;
    }

    /**
     * La méthode setExceptionFault permet de transférer une exception levée en la convertissant en exception
     * gérée par le web service
     * @param pE est l'Exception levée et récupérée
     * @return un objet {@link com.antazri.generated.auth.ExceptionFault}
     */
    private ExceptionFault setExceptionFault(com.antazri.bo.exception.BadLoginException pE) {
        Fault fault = new Fault();
        fault.setFaultCode(pE.getFault().getFaultCode());
        fault.setFaultString(pE.getFault().getFaultString());

        ExceptionFault exceptionFault = new ExceptionFault();
        exceptionFault.setFault(fault);

        return exceptionFault;
    }
}
