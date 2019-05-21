package com.antazri.service.impl;

import com.antazri.bo.contract.ICategoryBo;
import com.antazri.generated.category.*;
import com.antazri.mapper.contract.IGenericConverter;
import com.antazri.mapper.impl.GenericConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * La classe CategoryManagement est l'implémentation du service CategoryPort généré par Apache CXF via le
 * fichier WSDL categorymanagement.wsdl. Elle utilise le Logger fourni par Log4J2 pour le logging ainsi que le
 * mapper générique {@link com.antazri.mapper.impl.GenericConverter}
 */
@Service
public class CategoryManagement implements CategoryPort {

    private static final Logger logger = LogManager.getLogger(CategoryManagement.class);

    @Autowired
    private ICategoryBo categoryBo;

    private IGenericConverter<Category, com.antazri.model.bean.Category> vGenericConverterForDatabase = new GenericConverter<>();
    private IGenericConverter<com.antazri.model.bean.Category, Category> vGenericConverterForWebService = new GenericConverter<>();

    /**
     * La méthode findById permet de retrouver une instance de Category selon son identifiant unique
     * @param parameters est un FindByIdRequest contenant l'ID envoyé au web service
     * @return une FindByIdResponse contenant les informations de la réponse du web service
     * @throws com.antazri.generated.author.ConvertException
     */
    @Override
    public FindByIdResponse findById(FindByIdRequest parameters) throws ConvertException {
        FindByIdResponse findByIdResponse = new FindByIdResponse();

        try {
            findByIdResponse.setCategory(vGenericConverterForWebService.toConvert(categoryBo.findById(parameters.getId()), new Category()));
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error("error.message.mapper.convert");
            throw new ConvertException(pE.getMessage(), setExceptionFault(pE));
        }

        return findByIdResponse;
    }

    /**
     * La méthode addCategory permet au WebService d'entregistrer une nouvelle instance d'un objet Category fourni via
     * AddCategoryRequest spécifié en paramètre
     * @param parameters est un objet AddCategoryRequest contenant les informations à transférer au Web Service
     * @return une AddCategoryResponse contenant les informations de la réponse du web service
     * @throws com.antazri.generated.author.ConvertException
     */
    @Override
    public AddCategoryResponse addCategory(AddCategoryRequest parameters) throws ConvertException {
        AddCategoryResponse addCategoryResponse = new AddCategoryResponse();
        com.antazri.model.bean.Category vCategoryDatabase;

        try {
            vCategoryDatabase = categoryBo.add(vGenericConverterForDatabase.toConvert(parameters.getCategory(), new com.antazri.model.bean.Category()));
            addCategoryResponse.setCategory(vGenericConverterForWebService.toConvert(vCategoryDatabase, new Category()));
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error("error.message.mapper.convert");
            throw new ConvertException(pE.getMessage(), setExceptionFault(pE));
        }

        return addCategoryResponse;
    }

    /**
     * La méthode updateCategory permet au WebService de mettre à jour une instance d'un objet Category fourni via
     * UpdateCategoryRequest spécifié en paramètre
     * @param parameters est un objet UpdateCategoryRequest contenant les informations à transférer au Web Service
     * @return une UpdateCategoryResponse contenant les informations de la réponse du web service
     * @throws com.antazri.generated.author.ConvertException
     */
    @Override
    public UpdateCategoryResponse updateCategory(UpdateCategoryRequest parameters) throws ConvertException {
        UpdateCategoryResponse updateCategoryResponse = new UpdateCategoryResponse();
        com.antazri.model.bean.Category vCategoryDatabase;

        try {
            vCategoryDatabase = categoryBo.update(vGenericConverterForDatabase.toConvert(parameters.getCategory(), categoryBo.findById(parameters.getCategory().getId())));
            updateCategoryResponse.setCategory(vGenericConverterForWebService.toConvert(vCategoryDatabase, new Category()));
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error("error.message.mapper.convert");
            throw new ConvertException(pE.getMessage(), setExceptionFault(pE));
        }

        return updateCategoryResponse;
    }

    /**
     * La méhode findAll permet de retourner l'ensemble des occurences de Author de la base de données
     * @param parameters est un FindAllRequest
     * @return une FindAllResponse contenant les informations de la réponse du web service
     * @throws com.antazri.generated.author.ConvertException
     */
    @Override
    public FindAllResponse findAll(FindAllRequest parameters) throws ConvertException {
        FindAllResponse findAllResponse = new FindAllResponse();
        List<com.antazri.model.bean.Category> vCategoriesDatabase = categoryBo.findAll();

        try {
            for (com.antazri.model.bean.Category category : vCategoriesDatabase) {
                findAllResponse.getCategories().add(vGenericConverterForWebService.toConvert(category, new Category()));
            }
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error("error.message.mapper.convert");
            throw new ConvertException(pE.getMessage(), setExceptionFault(pE));
        }

        return findAllResponse;
    }

    /**
     * La méthode deleteCategory permet de supprimer une instance de Category de la base de données via son identifiant
     * unique fourni à la requête
     * @param parameters est un DeleteCategoryRequest contenant l'identifiant de l'occurence et envoyé au web service
     * @return une DeleteCategoryResponse contenant les informations de la réponse du web service
     * @throws com.antazri.generated.author.ConvertException
     */
    @Override
    public DeleteCategoryResponse deleteCategory(DeleteCategoryRequest parameters) throws ConvertException {
        DeleteCategoryResponse vDeleteCategoryResponse = new DeleteCategoryResponse();

        categoryBo.delete(categoryBo.findById(parameters.getCategory().getId()));
        vDeleteCategoryResponse.setDeleted(true);

        return vDeleteCategoryResponse;
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
