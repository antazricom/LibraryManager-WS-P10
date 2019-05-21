package com.antazri.service.impl;

import com.antazri.bo.contract.IAuthorBo;
import com.antazri.generated.author.*;
import com.antazri.mapper.contract.IGenericConverter;
import com.antazri.mapper.impl.GenericConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * La classe AuthorManagement est l'implémentation du service AuthorPort généré par Apache CXF via le
 * fichier WSDL authormanagement.wsdl. Elle utilise le Logger fourni par Log4J2 pour le logging ainsi que le
 * mapper générique {@link com.antazri.mapper.impl.GenericConverter}
 */
@Service
public class AuthorManagement implements AuthorPort {

    private static final Logger logger = LogManager.getLogger(AuthorManagement.class);

    @Autowired
    private IAuthorBo authorBo;

    private IGenericConverter<Author, com.antazri.model.bean.Author> vGenericConverterForDatabase = new GenericConverter<>();
    private IGenericConverter<com.antazri.model.bean.Author, Author> vGenericConverterForWebService = new GenericConverter<>();

    /**
     * La méthode addAuthor permet au WebService d'entregistrer une nouvelle instance d'un objet Author fourni via
     * AddAuthorRequest spécifié en paramètre
     * @param parameters est un objet AddAuthorRequest contenant les informations à transférer au Web Service
     * @return une AddAuthorResponse contenant les informations de la réponse du web service
     * @throws ConvertException
     */
    @Override
    public AddAuthorResponse addAuthor(AddAuthorRequest parameters) throws ConvertException {
        AddAuthorResponse vAddAuthorResponse = new AddAuthorResponse();

        com.antazri.model.bean.Author vAuthorDatabase;
        Author vAuthor;

        try {
            vAuthorDatabase = vGenericConverterForDatabase.toConvert(parameters.getAuthor(), new com.antazri.model.bean.Author());
            vAuthorDatabase = authorBo.add(vAuthorDatabase);
            vAuthor = vGenericConverterForWebService.toConvert(vAuthorDatabase, new Author());
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error("error.message.mapper.convert");
            throw new ConvertException(pE.getMessage(), setExceptionFault(pE));
        }

        vAddAuthorResponse.setAuthor(vAuthor);

        return vAddAuthorResponse;
    }

    /**
     * La méthode updateAuthor permet au WebService de mettre à jour une instance d'un objet Author fourni via
     * UpdateAuthorRequest spécifié en paramètre
     * @param parameters est un objet UpdateAuthorRequest contenant les informations à transférer au Web Service
     * @return une UpdateAuthorResponse contenant les informations de la réponse du web service
     * @throws ConvertException
     */
    @Override
    public UpdateAuthorResponse updateAuthor(UpdateAuthorRequest parameters) throws ConvertException {
        UpdateAuthorResponse vUpdateAuthorResponse = new UpdateAuthorResponse();

        com.antazri.model.bean.Author vAuthorDatabase;
        Author vAuthor;

        try {
            vAuthorDatabase = vGenericConverterForDatabase.toConvert(parameters.getAuthor(), authorBo.findById(parameters.getAuthor().getId()));
            vAuthorDatabase = authorBo.update(vAuthorDatabase);
            vAuthor = vGenericConverterForWebService.toConvert(vAuthorDatabase, new Author());
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error("error.message.mapper.convert");
            throw new ConvertException(pE.getMessage(), setExceptionFault(pE));
        }

        vUpdateAuthorResponse.setAuthor(vAuthor);

        return vUpdateAuthorResponse;
    }

    /**
     * La méthode findById permet de retrouver une instance de Author selon son identifiant unique
     * @param parameters est un FindByIdRequest contenant l'ID envoyé au web service
     * @return une FindByIdResponse contenant les informations de la réponse du web service
     * @throws ConvertException
     */
    @Override
    public FindByIdResponse findById(FindByIdRequest parameters) throws ConvertException {
        FindByIdResponse vFindByIdResponse = new FindByIdResponse();

        try {
            vFindByIdResponse.setAuthor(vGenericConverterForWebService.toConvert(authorBo.findById(parameters.getId()), new Author()));
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error("error.message.mapper.convert");
            throw new ConvertException(pE.getMessage(), setExceptionFault(pE));
        }

        return vFindByIdResponse;
    }

    /**
     * La méthode deleteAuthor permet de supprimer une instance de Author de la base de données via son identifiant
     * unique fourni à la requête
     * @param parameters est un DeleteAuthorRequest contenant l'identifiant de l'occurence et envoyé au web service
     * @return une DeleteAuthorResponse contenant les informations de la réponse du web service
     * @throws ConvertException
     */
    @Override
    public DeleteAuthorResponse deleteAuthor(DeleteAuthorRequest parameters) throws ConvertException {
        DeleteAuthorResponse vDeleteAuthorResponse = new DeleteAuthorResponse();

        authorBo.delete(authorBo.findById(parameters.getAuthor().getId()));
        vDeleteAuthorResponse.setDeleted(true);

        return vDeleteAuthorResponse;
    }

    /**
     * La méhode findAll permet de retourner l'ensemble des occurences de Category de la base de données
     * @param parameters est un FindAllRequest
     * @return une FindAllResponse contenant les informations de la réponse du web service
     * @throws ConvertException
     */
    @Override
    public FindAllResponse findAll(FindAllRequest parameters) throws ConvertException {
        FindAllResponse vFindAllResponse = new FindAllResponse();
        List<com.antazri.model.bean.Author> vAuthorsDatabase = authorBo.findAll();

        try {
            for (com.antazri.model.bean.Author author : vAuthorsDatabase) {
                vFindAllResponse.getAuthors().add(vGenericConverterForWebService.toConvert(author, new Author()));
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
}
