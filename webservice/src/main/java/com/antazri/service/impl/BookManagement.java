package com.antazri.service.impl;

import com.antazri.bo.contract.IAuthorBo;
import com.antazri.bo.contract.IBookBo;
import com.antazri.bo.contract.ICategoryBo;
import com.antazri.bo.contract.ILoanBo;
import com.antazri.generated.book.*;
import com.antazri.mapper.contract.IDateConverter;
import com.antazri.mapper.contract.IGenericConverter;
import com.antazri.mapper.impl.DateConverter;
import com.antazri.mapper.impl.GenericConverter;
import com.antazri.model.bean.Loan;
import com.antazri.model.utils.ErrorMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * La classe BookManagement est l'implémentation de l'interface BookPort du web service BookManagementService généré par Apache CXF via le
 * fichier WSDL bookmanagement.wsdl. Elle utilise le Logger fourni par Log4J2 pour le logging ainsi que le
 * mapper générique {@link com.antazri.mapper.impl.GenericConverter}
 */
@Service
public class BookManagement implements BookPort {

    private static final Logger logger = LogManager.getLogger(BookManagement.class);

    @Autowired
    private IBookBo bookBo;

    @Autowired
    private IAuthorBo authorBo;

    @Autowired
    private ICategoryBo categoryBo;

    @Autowired
    private ILoanBo loanBo;

    private IGenericConverter<Book, com.antazri.model.bean.Book> vGenericConverterForDatabase = new GenericConverter<>();
    private IGenericConverter<com.antazri.model.bean.Book, Book> vGenericConverterForWebService = new GenericConverter<>();
    private IGenericConverter<Author, com.antazri.model.bean.Author> vAuthorGenericConverterForDatabase = new GenericConverter<>();
    private IGenericConverter<com.antazri.model.bean.Author, Author> vAuthorGenericConverterForWebService = new GenericConverter<>();
    private IGenericConverter<Category, com.antazri.model.bean.Category> vCategoryGenericConverterForDatabase = new GenericConverter<>();
    private IGenericConverter<com.antazri.model.bean.Category, Category> vCategoryGenericConverterForWebService = new GenericConverter<>();
    private IDateConverter vDateConverter = new DateConverter();

    /**
     * La méthode findAll permet de retourner l'ensemble des occurences de Book enreghistrées dans la base de données
     * @param parameters est un objet FindAllRequest
     * @return un objet FindAllResponse contenant l'ensemble des informations sur les instances de Book retournées
     * depuis la base de données
     * @throws com.antazri.generated.member.ConvertException
     */
    @Override
    public FindAllResponse findAll(FindAllRequest parameters) throws ConvertException {
        FindAllResponse vFindAllResponse = new FindAllResponse();
        List<com.antazri.model.bean.Book> vBooksDatabase = bookBo.findAll();

        try {
            for (com.antazri.model.bean.Book book : vBooksDatabase) {
                vFindAllResponse.getBooks().add(
                        convertPropertiesToWebService(book,
                                vGenericConverterForWebService.toConvert(book, new Book())));
            }
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error(ErrorMessage.getErrorMessages().getString("error.message.mapper.convert") + " (findAll operation)");
            throw new ConvertException(pE.getMessage(), setExceptionFault(pE));
        }

        return vFindAllResponse;
    }

    /**
     * La méthode addBook permet d'ajouter une nouvelle instance de Book dans la base de données via le web service
     * @param parameters est un objet AddBookRequest contenant l'objet Book à enregistrer dans la base de données
     * @return un objet AddBookResponse contenant les données retournées par le web service après l'ajout dans
     * la base de données
     * @throws ConvertException
     */
    @Override
    public AddBookResponse addBook(AddBookRequest parameters) throws ConvertException {
        AddBookResponse vAddBookResponse = new AddBookResponse();
        com.antazri.model.bean.Book vBookDatabase;

        try {
            vBookDatabase = bookBo.add(
                    convertPropertiesToDatabase(
                            vGenericConverterForDatabase.toConvert(parameters.getBook(), new com.antazri.model.bean.Book()), parameters.getBook()));
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error(ErrorMessage.getErrorMessages().getString("error.message.mapper.convert") + " (addBook:Add to Database operation)");
            throw new ConvertException(pE.getMessage(), setExceptionFault(pE));
        }

        try {
            vAddBookResponse.setBook(convertPropertiesToWebService(vBookDatabase, vGenericConverterForWebService.toConvert(vBookDatabase, new Book())));
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error(ErrorMessage.getErrorMessages().getString("error.message.mapper.convert") + " (addBook:Return to WebService operation)");
            throw new ConvertException(pE.getMessage(), setExceptionFault(pE));
        }

        return vAddBookResponse;
    }

    /**
     * La méthode findByAuthor permet de retourner l'ensemble des instances de Book selon l'attribut "author_id" définissant
     * l'identifiant unique d'une instance de Author dans la base de données
     * @param parameters est un objet FindByAuthorRequest contenant l'identifiant unique (Integer) de l'instance Author concernée
     * @return un objet FindByAuthorResponse contenant une liste d'objet Book
     * @throws ConvertException
     */
    @Override
    public FindByAuthorResponse findByAuthor(FindByAuthorRequest parameters) throws ConvertException {
        FindByAuthorResponse vFindByAuthorResponse = new FindByAuthorResponse();
        List<com.antazri.model.bean.Book> vBooksDatabase;

        try {
            vBooksDatabase = bookBo.findByAuthor(vAuthorGenericConverterForDatabase.toConvert(parameters.getAuthor(), authorBo.findById(parameters.getAuthor().getId())));
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error(ErrorMessage.getErrorMessages().getString("error.message.mapper.convert") + " (findByAuthor:Get Books from Database operation)");
            throw new ConvertException(pE.getMessage(), setExceptionFault(pE));
        }

        try {
            for (com.antazri.model.bean.Book book : vBooksDatabase) {
                Book vBook = convertPropertiesToWebService(book, vGenericConverterForWebService.toConvert(book, new Book()));
                vFindByAuthorResponse.getBooks().add(vBook);
            }
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error(ErrorMessage.getErrorMessages().getString("error.message.mapper.convert") + " (findByAuthor:Return to WebService operation)");
            throw new ConvertException(pE.getMessage(), setExceptionFault(pE));
        }

        return vFindByAuthorResponse;
    }

    /**
     * La méthode findByCategory permet de retourner l'ensemble des instances de Book selon l'attribut "category_id" définissant
     * l'identifiant unique d'une instance de Category dans la base de données
     * @param parameters est un objet FindByCategoryRequest contenant l'identifiant unique (Integer) de l'instance Author concernée
     * @return un objet FindByCategoryResponse contenant une liste d'objet Book
     * @throws ConvertException
     */
    @Override
    public FindByCategoryResponse findByCategory(FindByCategoryRequest parameters) throws ConvertException {
        FindByCategoryResponse vFindByCategoryResponse = new FindByCategoryResponse();
        List<com.antazri.model.bean.Book> vBooksDatabase;

        try {
            vBooksDatabase = bookBo.findByCategory(vCategoryGenericConverterForDatabase.toConvert(parameters.getCategory(), categoryBo.findById(parameters.getCategory().getId())));
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error(ErrorMessage.getErrorMessages().getString("error.message.mapper.convert") + " (findByCategory:Get Books from Database operation)");
            throw new ConvertException(pE.getMessage(), setExceptionFault(pE));
        }

        try {
            for (com.antazri.model.bean.Book book : vBooksDatabase) {
                Book vBook = convertPropertiesToWebService(book, vGenericConverterForWebService.toConvert(book, new Book()));
                vFindByCategoryResponse.getBooks().add(vBook);
            }
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error(ErrorMessage.getErrorMessages().getString("error.message.mapper.convert") + " (findByCategory:Return to WebService operation)");
            throw new ConvertException(pE.getMessage(), setExceptionFault(pE));
        }

        return vFindByCategoryResponse;
    }

    /**
     * La méthode findByTitle permet de retrouver l'ensemble des instances de Book dont l'attribut "title" contient la chaîne de
     * caractères passée via l'objet FindByTitleRequest
     * @param parameters est un objet FindByTitleRequest contenant la chaîne de caractères à utiliser par la requête SQL
     *                   du web service
     * @return un objet FindByTitleResponse contenant une liste d'objets Book ayant un attribut "title" correspondant à la condition définie
     * @throws ConvertException
     */
    @Override
    public FindByTitleResponse findByTitle(FindByTitleRequest parameters) throws ConvertException {
        FindByTitleResponse vFindByTitleResponse = new FindByTitleResponse();
        List<com.antazri.model.bean.Book> vBooksDatabase = bookBo.findByTitle(parameters.getTitle());

        try {
            for (com.antazri.model.bean.Book book : vBooksDatabase) {
                Book vBook = convertPropertiesToWebService(book, vGenericConverterForWebService.toConvert(book, new Book()));
                vFindByTitleResponse.getBooks().add(vBook);
            }
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error(ErrorMessage.getErrorMessages().getString("error.message.mapper.convert") + " (findByTitle:Return to WebService operation)");
            throw new ConvertException(pE.getMessage(), setExceptionFault(pE));
        }

        return vFindByTitleResponse;
    }

    /**
     * La méthode findById permet de retourner une instance de Book selon son identifiant unique via le web service
     * @param parameters est un objet FindByIdRequest contenant l'identifiant unique (Integer) demandé par le
     *                   web service
     * @return un objet FindByIdResponse contenant l'objet Book identifié par le web service
     * @throws ConvertException
     */
    @Override
    public FindByIdResponse findById(FindByIdRequest parameters) throws ConvertException {
        FindByIdResponse vFindByIdResponse = new FindByIdResponse();

        try {
            com.antazri.model.bean.Book vBookDatabase = bookBo.findById(parameters.getId());
            vFindByIdResponse.setBook(convertPropertiesToWebService(vBookDatabase, vGenericConverterForWebService.toConvert(vBookDatabase, new Book())));
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error(ErrorMessage.getErrorMessages().getString("error.message.mapper.convert") + " (findById:Return to WebService operation)");
            throw new ConvertException(pE.getMessage(), setExceptionFault(pE));
        }

        return vFindByIdResponse;
    }

    /**
     * La méthode updateBook permet de mettre à jour une instance de Book dans la base de données via le web service
     * @param parameters est un objet UpdateBookRequest contenant l'objet Book à enregistrer dans la base de données
     * @return un objet UpdateBookResponse contenant les données retournées par le web service après l'ajout dans
     * la base de données
     * @throws ConvertException
     */
    @Override
    public UpdateBookResponse updateBook(UpdateBookRequest parameters) throws ConvertException {
        UpdateBookResponse vUpdateBookResponse = new UpdateBookResponse();
        com.antazri.model.bean.Book vBookDatabase;

        try {
            vBookDatabase = convertPropertiesToDatabase(vGenericConverterForDatabase.toConvert(parameters.getBook(), bookBo.findById(parameters.getBook().getId())), parameters.getBook());
            vBookDatabase = bookBo.update(vBookDatabase);
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error(ErrorMessage.getErrorMessages().getString("error.message.mapper.convert") + " (updateBook:Upload to Database operation)");
            throw new ConvertException(pE.getMessage(), setExceptionFault(pE));
        }

        try {
            vUpdateBookResponse.setBook(convertPropertiesToWebService(vBookDatabase, vGenericConverterForWebService.toConvert(vBookDatabase, new Book())));
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error(ErrorMessage.getErrorMessages().getString("error.message.mapper.convert") + " (updateBook:Return to WebService operation)");
            throw new ConvertException(pE.getMessage(), setExceptionFault(pE));
        }

        return vUpdateBookResponse;
    }

    /**
     * La méthode deleteBook permet de supprimer une instance de Book dans la base de données via le web service
     * @param parameters est un objet DeleteBookRequest contenant l'objet Book à enregistrer dans la base de données
     * @return un objet DeleteBookResponse contenant les données retournées par le web service après l'ajout dans
     * la base de données
     * @throws ConvertException
     */
    @Override
    public DeleteBookResponse deleteBook(DeleteBookRequest parameters) throws ConvertException {
        DeleteBookResponse vDeleteBookResponse = new DeleteBookResponse();

        bookBo.delete(bookBo.findById(parameters.getBook().getId()));
        vDeleteBookResponse.setDeleted(true);

        return vDeleteBookResponse;
    }

    /**
     * La méthode getAvailableCopies permet de retourner, via le web service, le nombre de copies disponibles en
     * fonction du nombre de copies (attribut "copeis" de Book) global et du nombre de Loan en cours lié au Book
     * @param parameters est un objet GetAvailableCopiesRequest contenant l'objet Book concerné
     * @return un objet GetAvailableCopiesResponse contenant l'ensemble des informations
     * @throws BookException
     */
    @Override
    public GetAvailableCopiesResponse getAvailableCopies(GetAvailableCopiesRequest parameters) throws BookException {
        GetAvailableCopiesResponse vGetAvailableCopiesResponse = new GetAvailableCopiesResponse();
        List<Loan> loansByBook;

        try {
            loansByBook = loanBo.findByBook(vGenericConverterForDatabase.toConvert(parameters.getBook(), new com.antazri.model.bean.Book()));
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error(ErrorMessage.getErrorMessages().getString("error.message.mapper.convert") + " (convertPropertiesToWebService)");
            throw new BookException(pE.getMessage(), setExceptionFault(pE));
        }

        vGetAvailableCopiesResponse.setCopies(parameters.getBook().getCopies() - loansByBook.size());

        if (vGetAvailableCopiesResponse.getCopies() <= 0) {
            logger.error(ErrorMessage.getErrorMessages().getString("error.message.mapper.convert") + " (convertPropertiesToWebService)");
            throw new BookException(ErrorMessage.getErrorMessages().getString("error.message.copies.available"));
        }

        return vGetAvailableCopiesResponse;
    }

    private ExceptionFault setExceptionFault(com.antazri.exception.ConvertException pE) {
        Fault fault = new Fault();
        fault.setFaultCode(pE.getFault().getFaultCode());
        fault.setFaultString(pE.getFault().getFaultString());

        ExceptionFault exceptionFault = new ExceptionFault();
        exceptionFault.setFault(fault);

        return exceptionFault;
    }

    /**
     * La méthode convertPropertiesToWebService permet de convertir l'ensemble des propriétés d'un objet Book issu de la
     * base de données en propriétés utilisable par le web service
     * @param pBookDatabase l'objet Book retourné par la base de données
     * @param pBookWebService un objet Book utilisable par le web service
     * @return un objet Book utilisable par le web service
     * @throws ConvertException
     */
    private Book convertPropertiesToWebService(com.antazri.model.bean.Book pBookDatabase, Book pBookWebService) throws ConvertException{
        try {
            pBookWebService.setPublicationDate(vDateConverter.toXMLGregorianCalendar(pBookDatabase.getPublicationDate()));
            pBookWebService.setAuthor(vAuthorGenericConverterForWebService.toConvert(authorBo.findById(pBookDatabase.getAuthor().getId()), new Author()));
            pBookWebService.setCategory(vCategoryGenericConverterForWebService.toConvert(categoryBo.findById(pBookDatabase.getCategory().getId()), new Category()));
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error(ErrorMessage.getErrorMessages().getString("error.message.mapper.convert") + " (convertPropertiesToWebService)");
            throw new ConvertException(pE.getMessage(), setExceptionFault(pE));
        }

        return pBookWebService;
    }

    /**
     * La méthode convertPropertiesToDatabase permet de convertir l'ensemble des propriétés d'un objet Book issu
     * du web service  en propriétés utilisable par la base de données
     * @param pBookDatabase l'objet Book retourné par la base de données
     * @param pBookWebService un objet Book utilisable par le web service
     * @return un objet Book utilisable par la base de données
     * @throws ConvertException
     */
    private com.antazri.model.bean.Book convertPropertiesToDatabase(com.antazri.model.bean.Book pBookDatabase, Book pBookWebService) throws ConvertException{
        try {
            pBookDatabase.setPublicationDate(vDateConverter.toDate(pBookWebService.getPublicationDate()));
            pBookDatabase.setAuthor(vAuthorGenericConverterForDatabase.toConvert(pBookWebService.getAuthor(), authorBo.findById(pBookWebService.getAuthor().getId())));
            pBookDatabase.setCategory(vCategoryGenericConverterForDatabase.toConvert(pBookWebService.getCategory(), categoryBo.findById(pBookWebService.getCategory().getId())));
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error(ErrorMessage.getErrorMessages().getString("error.message.mapper.convert") + " (convertPropertiesToDatabase)");
            throw new ConvertException(pE.getMessage(), setExceptionFault(pE));
        }

        return pBookDatabase;
    }

}
