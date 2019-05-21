package com.antazri.service.impl;

import com.antazri.bo.contract.IMemberBo;
import com.antazri.generated.member.*;
import com.antazri.mapper.contract.IGenericConverter;
import com.antazri.mapper.impl.GenericConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * La classe MemberManagement est l'implémentation de l'interface MemberPort du web service MemberManagementService généré par Apache CXF via le
 * fichier WSDL membermanagement.wsdl. Elle utilise le Logger fourni par Log4J2 pour le logging ainsi que le
 * mapper générique {@link com.antazri.mapper.impl.GenericConverter}
 */
@Service
public class MemberManagement implements MemberPort {

    private static final Logger logger = LogManager.getLogger(MemberManagement.class);

    @Autowired
    private IMemberBo memberBo;

    private IGenericConverter<Member, com.antazri.model.bean.Member> vConverterToDatabase = new GenericConverter<>();
    private IGenericConverter<com.antazri.model.bean.Member, Member> vConverterToWebService = new GenericConverter<>();

    /**
     * La méthode addMember permet d'ajouter une nouvelle occurence de Member via le WebService en envoyant un objet
     * AddMemberRequest contenant l'ensemble des informations nécessaires
     * @param parameters est un objet AddMemberRequest contenant l'objet Member demandé par le web service
     * @return un objet AddMemberResponse contenant l'ensemble des données de la réponse du web service
     * @throws ConvertException
     */
    @Override
    public AddMemberResponse addMember(AddMemberRequest parameters) throws ConvertException {
        AddMemberResponse vAddMemberResponse = new AddMemberResponse();

        try {
            com.antazri.model.bean.Member vMember = memberBo.add(vConverterToDatabase.toConvert(parameters.getMember(), new com.antazri.model.bean.Member()));
            vAddMemberResponse.setMember(vConverterToWebService.toConvert(vMember, new Member()));
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error("error.message.mapper.convert");
            throw new ConvertException(pE.getMessage(), setExceptionFault(pE));
        }

        return vAddMemberResponse;
    }

    /**
     * La méthode updateMember permet de mettre à jour une occurence de Member via le WebService en envoyant un objet
     * UpdateMemberRequest contenant l'ensemble des informations nécessaires
     * @param parameters est un objet UpdateMemberRequest contenant l'objet Member demandé par le web service
     * @return un objet UpdateMemberResponse contenant l'ensemble des données de la réponse du web service
     * @throws ConvertException
     */
    @Override
    public UpdateMemberResponse updateMember(UpdateMemberRequest parameters) throws ConvertException {
        UpdateMemberResponse vUpdateMemberResponse = new UpdateMemberResponse();

        try {
            com.antazri.model.bean.Member vMember = memberBo.update(vConverterToDatabase.toConvert(parameters.getMember(), memberBo.findById(parameters.getMember().getId())));
            vUpdateMemberResponse.setMember(vConverterToWebService.toConvert(vMember, new Member()));
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error("error.message.mapper.convert");
            throw new ConvertException(pE.getMessage(), setExceptionFault(pE));
        }

        return vUpdateMemberResponse;
    }

    /**
     * La méthode findById permet de retourner une instance de Member selon son identifiant unique via le web service
     * @param parameters est un objet FindByIdRequest contenant l'identifiant unique (Integer) demandé par le
     *                   web service
     * @return un objet FindByIdResponse contenant l'objet Member identifié par le web service
     * @throws ConvertException
     */
    @Override
    public FindByIdResponse findById(FindByIdRequest parameters) throws ConvertException {
        FindByIdResponse vFindByIdResponse = new FindByIdResponse();

        try {
            vFindByIdResponse.setMember(vConverterToWebService.toConvert(memberBo.findById(parameters.getId()), new Member()));
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error("error.message.mapper.convert");
            throw new ConvertException(pE.getMessage(), setExceptionFault(pE));
        }

        return vFindByIdResponse;
    }

    /**
     * La méthode deleteMember permet de supprimer une occurence de Member via le WebService en envoyant un objet
     * DeleteMemberRequest contenant l'ensemble des informations nécessaires
     * @param parameters est un objet DeleteMemberRequest contenant l'objet Member demandé par le web service
     * @return un objet UpdateMemberResponse contenant l'ensemble des données de la réponse du web service
     * @throws ConvertException
     */
    @Override
    public DeleteMemberResponse deleteMember(DeleteMemberRequest parameters) throws ConvertException {
        DeleteMemberResponse vDeleteMemberResponse = new DeleteMemberResponse();

        memberBo.delete(memberBo.findById(parameters.getMember().getId()));
        vDeleteMemberResponse.setDeleted(true);

        return vDeleteMemberResponse;
    }

    /**
     * La méthode findByLogin permet de retourner une instance de Member selon son attribut "login" via le web service
     * @param parameters est un objet FindByLoginRequest contenant le "login" (String) demandé par le
     *                   web service
     * @return un objet FindByLoginResponse contenant l'objet Member identifié par le web service
     * @throws ConvertException
     */
    @Override
    public FindByLoginResponse findByLogin(FindByLoginRequest parameters) throws ConvertException, BadLoginException {
        FindByLoginResponse vFindByLoginResponse = new FindByLoginResponse();

        com.antazri.model.bean.Member vMemberDatabase;

        try {
            vMemberDatabase = memberBo.findByLogin(parameters.getLogin());
        } catch (com.antazri.bo.exception.BadLoginException pE) {
            logger.error("error.message.badlogin");
            throw new BadLoginException(pE.getMessage(), setBadLoginExceptionFault(pE));
        }

        try {
            vFindByLoginResponse.setMember(vConverterToWebService.toConvert(vMemberDatabase, new Member()));
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error("error.message.mapper.convert");
            throw new ConvertException(pE.getMessage(), setExceptionFault(pE));
        }

        return vFindByLoginResponse;
    }

    /**
     * La méthode findAll permet de retourner l'ensemble des occurences de Member enreghistrées dans la base de données
     * @param parameters est un objet FindAllRequest
     * @return un objet FindAllResponse contenant l'ensemble des informations sur les instances de Member retournées
     * depuis la base de données
     * @throws ConvertException
     */
    @Override
    public FindAllResponse findAll(FindAllRequest parameters) throws ConvertException {
        FindAllResponse vFindAllResponse = new FindAllResponse();

        List<com.antazri.model.bean.Member> vMembersDatabase = memberBo.findAll();

        try {
            for (com.antazri.model.bean.Member member : vMembersDatabase) {
                vFindAllResponse.getMembers().add(vConverterToWebService.toConvert(member, new Member()));
            }
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error("error.message.mapper.convert");
            throw new ConvertException(pE.getMessage(), setExceptionFault(pE));
        }

        return vFindAllResponse;
    }

    private ExceptionFault setExceptionFault(com.antazri.exception.ConvertException pE) {
        Fault fault = new Fault();
        fault.setFaultCode(pE.getFault().getFaultCode());
        fault.setFaultString(pE.getFault().getFaultString());

        ExceptionFault exceptionFault = new ExceptionFault();
        exceptionFault.setFault(fault);

        return exceptionFault;
    }

    private ExceptionFault setBadLoginExceptionFault(com.antazri.bo.exception.BadLoginException pE) {
        Fault fault = new Fault();
        fault.setFaultCode(pE.getFault().getFaultCode());
        fault.setFaultString(pE.getFault().getFaultString());

        ExceptionFault exceptionFault = new ExceptionFault();
        exceptionFault.setFault(fault);

        return exceptionFault;
    }
}
