package com.antazri.service.impl;

import com.antazri.bo.contract.IBookBo;
import com.antazri.bo.contract.ILoanBo;
import com.antazri.bo.contract.IMemberBo;
import com.antazri.generated.loan.*;
import com.antazri.mapper.contract.IDateConverter;
import com.antazri.mapper.contract.IGenericConverter;
import com.antazri.mapper.impl.DateConverter;
import com.antazri.mapper.impl.GenericConverter;
import com.antazri.model.utils.AppMessage;
import com.antazri.bo.utils.contract.IDateExtension;
import com.antazri.bo.utils.impl.DateExtension;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * La classe LoanManagement est l'implémentation de l'interface LoanPort du web service LoanManagementService généré par Apache CXF via le
 * fichier WSDL loanmanagement.wsdl. Elle utilise le Logger fourni par Log4J2 pour le logging ainsi que le
 * mapper générique {@link com.antazri.mapper.impl.GenericConverter}
 */
@Service
public class LoanManagement implements LoanPort {

    private static final Logger logger = LogManager.getLogger(LoanManagement.class);
    private IDateExtension dateExtension = new DateExtension();

    @Autowired
    private ILoanBo loanBo;

    @Autowired
    private IBookBo bookBo;

    @Autowired
    private IMemberBo memberBo;

    private IGenericConverter<Loan, com.antazri.model.bean.Loan> vConverterToDatabase = new GenericConverter<>();
    private IGenericConverter<com.antazri.model.bean.Loan, Loan> vConverterToWebService = new GenericConverter<>();
    private IGenericConverter<com.antazri.generated.loan.Book, com.antazri.model.bean.Book> vBookGenericConverterForDatabase = new GenericConverter<>();
    private IGenericConverter<com.antazri.model.bean.Book, com.antazri.generated.loan.Book> vBookGenericConverterForWebService = new GenericConverter<>();
    private IGenericConverter<com.antazri.generated.loan.Member, com.antazri.model.bean.Member> vMemberGenericConverterForDatabase = new GenericConverter<>();
    private IGenericConverter<com.antazri.model.bean.Member, com.antazri.generated.loan.Member> vMemberGenericConverterForWebService = new GenericConverter<>();

    private IDateConverter vDateConverter = new DateConverter();

    /**
     * La méthode deleteLoan permet de supprimer une instance de Loan dans la base de données via le web service
     * @param parameters est un objet DeleteLoanRequest contenant l'objet Loan à supprimer
     * @return un objet DeleteLoanResponse contenant les informations liées à la requête (booléen vérifiant la suppression)
     * @throws ConvertException
     */
    @Override
    public DeleteLoanResponse deleteLoan(DeleteLoanRequest parameters) throws ConvertException {
        DeleteLoanResponse vDeleteLoanResponse = new DeleteLoanResponse();

        loanBo.delete(loanBo.findById(parameters.getLoan().getId()));
        vDeleteLoanResponse.setDeleted(true);

        return vDeleteLoanResponse;
    }

    /**
     * La méthode isEnding permet de retourner les instances de Loan dont l'attribut "DateEnd" est une date proche de
     * la date de la requête
     * @param parameters est un objet IsEndingRequest envoyé au web service
     * @return un objet IsEndingResponse contenant les objets Loan concernés
     * @throws ConvertException
     */
    @Override
    public IsEndingResponse isEnding(IsEndingRequest parameters) throws ConvertException {
        IsEndingResponse vIsEndingResponse = new IsEndingResponse();

        LocalDate vDateEnd;

        try {
            vDateEnd = LocalDate.parse(vDateConverter.toDate(parameters.getLoan().getDateEnd()).toString());
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error("error.message.mapper.convert");
            throw new ConvertException(pE.getMessage(), setConvertExceptionFault(pE));
        }

        if (vDateEnd.compareTo(LocalDate.now()) < 0) {
            vIsEndingResponse.setEnding(true);
        }

        return vIsEndingResponse;
    }

    /**
     * La méthode updateLoan permet de mettre à jour une instance de Loan dans la base de données via le web service
     * @param parameters est un objet UpdateLoanRequest contenant l'objet Loan à modifier
     * @return un objet UpdateLoanResponse contenant les informations liées à l'instance mis à jour
     * @throws ConvertException
     */
    @Override
    public UpdateLoanResponse updateLoan(UpdateLoanRequest parameters) throws ConvertException {
        UpdateLoanResponse vUpdateLoanResponse = new UpdateLoanResponse();

        try {
            com.antazri.model.bean.Loan vLoanDatabase = loanBo.update(
                    convertPropertiesToDatabase(loanBo.findById(parameters.getLoan().getId()), parameters.getLoan()));
            vUpdateLoanResponse.setLoan(convertPropertiesToWebService(vConverterToWebService.toConvert(vLoanDatabase, new Loan()), vLoanDatabase));
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error("error.message.mapper.convert");
            throw new ConvertException(pE.getMessage(), setConvertExceptionFault(pE));
        }

        return vUpdateLoanResponse;
    }

    /**
     * La méthode findById permet de retrouver une instance de Loan selon l'identifiant unique passé au web service
     * @param parameters est un objet FindByIdRequest contenant l'identifiant unique (integer) recherché
     * @return un objet FindByIdResponse contenant l'objet Loan retrouvé dans la base de données
     * @throws ConvertException
     */
    @Override
    public FindByIdResponse findById(FindByIdRequest parameters) throws ConvertException {
        FindByIdResponse vFindByIdResponse = new FindByIdResponse();

        try {
            vFindByIdResponse.setLoan(
                    convertPropertiesToWebService(
                            vConverterToWebService.toConvert(loanBo.findById(parameters.getId()), new Loan()), loanBo.findById(parameters.getId())));
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error("error.message.mapper.convert");
            throw new ConvertException(pE.getMessage(), setConvertExceptionFault(pE));
        }

        return vFindByIdResponse;
    }

    /**
     * La méthode addLoan permet d'enregistrer une nouvelle instance de Loan dans la base de données via le web service
     * @param parameters est un objet AddLoanRequest contenant l'objet Loan à ajouter
     * @return un objet AddLoanResponse contenant les informations liées à l'instance enregistrée
     * @throws ConvertException
     */
    @Override
    public AddLoanResponse addLoan(AddLoanRequest parameters) throws ConvertException {
        AddLoanResponse vAddLoanResponse = new AddLoanResponse();

        try {
            com.antazri.model.bean.Loan vLoanDatabase = loanBo.add(
                    convertPropertiesToDatabase(vConverterToDatabase.toConvert(parameters.getLoan(), new com.antazri.model.bean.Loan()), parameters.getLoan()));
            vAddLoanResponse.setLoan(convertPropertiesToWebService(vConverterToWebService.toConvert(vLoanDatabase, new Loan()), vLoanDatabase));
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error("error.message.mapper.convert");
            throw new ConvertException(pE.getMessage(), setConvertExceptionFault(pE));
        }

        return vAddLoanResponse;
    }

    /**
     * La méthode findByBook permet de retourner l'ensemble des instances de Loan dont l'attribut "book_id" correspond
     * à l'identifiant unique de l'objet Book passé au web service
     * @param parameters est un objet FindByBookRequest contenant l'objet Book concerné
     * @return un objet FindByBookResponse contenant une liste d'objets Loan
     * @throws ConvertException
     */
    @Override
    public FindByBookResponse findByBook(FindByBookRequest parameters) throws ConvertException {
        FindByBookResponse vFindByBookResponse = new FindByBookResponse();
        IGenericConverter<Book, com.antazri.model.bean.Book> vBookConverterToDatabase = new GenericConverter<>();

        try {
            List<com.antazri.model.bean.Loan> vLoansDatabase = loanBo.findByBook(vBookConverterToDatabase.toConvert(parameters.getBook(), bookBo.findById(parameters.getBook().getId())));

            for (com.antazri.model.bean.Loan loan : vLoansDatabase) {
                vFindByBookResponse.getLoans()
                        .add(convertPropertiesToWebService(vConverterToWebService.toConvert(loan, new Loan()), loan));
            }
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error("error.message.mapper.convert");
            throw new ConvertException(pE.getMessage(), setConvertExceptionFault(pE));
        }

        return vFindByBookResponse;
    }

    /**
     * La méthode findByMember permet de retourner l'ensemble des instances de Loan dont l'attribut "member_id" correspond
     * à l'identifiant unique de l'objet Member passé au web service
     * @param parameters est un objet FindByMemberRequest contenant l'objet Member concerné
     * @return un objet FindByMEmberResponse contenant une liste d'objets Loan
     * @throws ConvertException
     */
    @Override
    public FindByMemberResponse findByMember(FindByMemberRequest parameters) throws ConvertException {
        FindByMemberResponse vFindByMemberResponse = new FindByMemberResponse();
        IGenericConverter<Member, com.antazri.model.bean.Member> vMemberConverterToDatabase = new GenericConverter<>();

        try {
            List<com.antazri.model.bean.Loan> vLoansDatabase = loanBo.findByMember(vMemberConverterToDatabase.toConvert(parameters.getMember(), memberBo.findById(parameters.getMember().getId())));

            for (com.antazri.model.bean.Loan loan : vLoansDatabase) {
                vFindByMemberResponse.getLoans()
                        .add(convertPropertiesToWebService(vConverterToWebService.toConvert(loan, new Loan()), loan));
            }
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error("error.message.mapper.convert");
            throw new ConvertException(pE.getMessage(), setConvertExceptionFault(pE));
        }
        return vFindByMemberResponse;
    }

    /**
     * La méthode getStatus permet de retourner le status de l'objet Loan en fonction d'un processus défini par le métier
     * @param parameters est un objet GetStatusRequest
     * @return un objet GetStatusResponse
     * @throws ConvertException
     */
    @Override
    public GetStatusResponse getStatus(GetStatusRequest parameters) throws ConvertException {
        GetStatusResponse vGetStatusResponse = new GetStatusResponse();

        return vGetStatusResponse;
    }

    /**
     * La méthode findAll permet de retourner l'ensemble des instances de Loan enregistrées dans la base de données
     * @param parameters est un objet FindAllRequest demandé par le web service pour effectuer la requête
     * @return un objet FindAllResponse contenant l'ensemble des informations des Loan de la base de données
     * @throws ConvertException
     */
    @Override
    public FindAllResponse findAll(FindAllRequest parameters) throws ConvertException {
        FindAllResponse vFindAllResponse = new FindAllResponse();

        List<com.antazri.model.bean.Loan> vLoansDatabase = loanBo.findAll();

        try {
            for (com.antazri.model.bean.Loan loan : vLoansDatabase) {
                vFindAllResponse.getLoans()
                        .add(convertPropertiesToWebService(vConverterToWebService.toConvert(loan, new Loan()), loan));
            }
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error("error.message.mapper.convert");
            throw new ConvertException(pE.getMessage(), setConvertExceptionFault(pE));
        }

        return vFindAllResponse;
    }

    /**
     * La méthode returnLoan permet de mettre à jour un objet Loan défini comme "retourné" par l'application web dédié
     * à la bibliothèque. La méthode va modifier l'attribut "DateReturn" par la date de la requête et l'attribut
     * "returned" à "true"
     * @param parameters est un objet ReturnLoanRequest contenant l'objet Loan concerné
     * @return un ReturnLoanResponse contenant les informations mises à jour de l'objet Loan concerné
     * @throws ReturnException
     * @throws ConvertException
     */
    @Override
    public ReturnLoanResponse returnLoan(ReturnLoanRequest parameters) throws ReturnException, ConvertException {
        ReturnLoanResponse vReturnLoanResponse = new ReturnLoanResponse();

        if (parameters.getLoan().isReturned()) {
            logger.info("loan.returned.true");
            Fault fault = new Fault();
            fault.setFaultCode("401 : Unauthorized");
            fault.setFaultString("Le prêt a déjà été retourné");
            ExceptionFault exceptionFault = new ExceptionFault();
            exceptionFault.setFault(fault);
            throw new ReturnException(AppMessage.getAppMessages().getString("loan.returned.true"), exceptionFault);
        }

        try {
            com.antazri.model.bean.Loan vLoanDatabase = loanBo.findById(parameters.getLoan().getId());
            vLoanDatabase = loanBo.returnLoan(vLoanDatabase);
            vReturnLoanResponse.setLoan(convertPropertiesToWebService(
                    vConverterToWebService.toConvert(vLoanDatabase, parameters.getLoan()), vLoanDatabase));
        }  catch (com.antazri.exception.ConvertException pE) {
            logger.error("error.message.mapper.convert");
            throw new ConvertException(pE.getMessage(), setConvertExceptionFault(pE));
        }

        return vReturnLoanResponse;
    }

    /**
     * La méthode extendLoan permet de mettre à jour un objet Loan défini comme "prolongé" par l'application web dédié
     * à la bibliothèque. La méthode va modifier l'attribut "DateEnd" en fonction des propriétés fixées et l'attribut
     * "extended" à "true"
     * @param parameters est un objet ExtendLoanRequest contenant l'objet Loan concerné
     * @return un ExtendLoanResponse contenant les informations mises à jour de l'objet Loan concerné
     * @throws ReturnException
     * @throws ConvertException
     */
    @Override
    public ExtendLoanResponse extendLoan(ExtendLoanRequest parameters) throws ExtendException, ConvertException {
        ExtendLoanResponse vExtendLoanResponse = new ExtendLoanResponse();

        if (parameters.getLoan().isExtended()) {
            logger.info("loan.extended.true");
            Fault fault = new Fault();
            fault.setFaultCode("401 : Unauthorized");
            fault.setFaultString("Le prêt a déjà été prolongé");
            ExceptionFault exceptionFault = new ExceptionFault();
            exceptionFault.setFault(fault);
            throw new ExtendException(AppMessage.getAppMessages().getString("loan.extended.true"), exceptionFault);
        }

        try {
            com.antazri.model.bean.Loan vLoanDatabase = convertPropertiesToDatabase(
                    vConverterToDatabase.toConvert(parameters.getLoan(), loanBo.findById(parameters.getLoan().getId())), parameters.getLoan());
            vLoanDatabase = loanBo.extendLoan(vLoanDatabase);
            vExtendLoanResponse.setLoan(
                    convertPropertiesToWebService(
                            vConverterToWebService.toConvert(vLoanDatabase, new Loan()), vLoanDatabase));
        }  catch (com.antazri.exception.ConvertException pE) {
            logger.error("error.message.mapper.convert");
            throw new ConvertException(pE.getMessage(), setConvertExceptionFault(pE));
        }

        return vExtendLoanResponse;
    }

    private ExceptionFault setConvertExceptionFault(com.antazri.exception.ConvertException pE) {
        return setExceptionFault(pE.getFault().getFaultCode(), pE.getFault().getFaultString());
    }

    private ExceptionFault setReturnExceptionFault(com.antazri.exception.ConvertException pE) {
        return setExceptionFault(pE.getFault().getFaultCode(), pE.getFault().getFaultString());
    }

    private ExceptionFault setExtendExceptionFault(com.antazri.exception.ConvertException pE) {
        return setExceptionFault(pE.getFault().getFaultCode(), pE.getFault().getFaultString());
    }

    private ExceptionFault setExceptionFault(String pFaultCode, String pFaultString) {
        Fault fault = new Fault();
        fault.setFaultCode(pFaultCode);
        fault.setFaultString(pFaultString);

        ExceptionFault exceptionFault = new ExceptionFault();
        exceptionFault.setFault(fault);

        return exceptionFault;
    }

    /**
     * La méthode convertPropertiesToWebService permet de convertir les propriétés d'un objet issu de la base de données
     * pour être lisible et utilisable par le web service
     * @param pLoanWebService est un objet utilisable par le web service
     * @param pLoanDatabase est un objet généré depuis la base de données
     * @return un objet utilisable par le web service
     * @throws ConvertException
     */
    private Loan convertPropertiesToWebService(Loan pLoanWebService, com.antazri.model.bean.Loan pLoanDatabase) throws ConvertException {
        try {
            pLoanWebService.setBook(vBookGenericConverterForWebService.toConvert(pLoanDatabase.getBook(), new Book()));
            pLoanWebService.setMember(vMemberGenericConverterForWebService.toConvert(pLoanDatabase.getMember(), new Member()));
            pLoanWebService.setDateBegin(vDateConverter.toXMLGregorianCalendar(pLoanDatabase.getDateBegin()));
            pLoanWebService.setDateEnd(vDateConverter.toXMLGregorianCalendar(pLoanDatabase.getDateEnd()));
            pLoanWebService.setDateReturn(vDateConverter.toXMLGregorianCalendar(pLoanDatabase.getDateReturn()));
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error("error.message.mapper.convert");
            throw new ConvertException(pE.getMessage(), setConvertExceptionFault(pE));
        }

        return pLoanWebService;
    }

    /**
     * La méthode convertPropertiesToDatabase permet de convertir les propriétés d'un objet issu du web service
     * pour être lisible et utilisable par la base de données
     * @param pLoanWebService est un objet généré depuis le web service
     * @param pLoanDatabase est un objet utilisable par la base de données
     * @return un objet utilisable par la base de données
     * @throws ConvertException
     */
    private com.antazri.model.bean.Loan convertPropertiesToDatabase(com.antazri.model.bean.Loan pLoanDatabase, Loan pLoanWebService) throws ConvertException {
        try {
            pLoanDatabase.setBook(bookBo.findById(pLoanWebService.getBook().getId()));
            pLoanDatabase.setMember(memberBo.findById(pLoanWebService.getMember().getId()));
            pLoanDatabase.setDateBegin(vDateConverter.toDate(pLoanWebService.getDateBegin()));
            pLoanDatabase.setDateEnd(vDateConverter.toDate(pLoanWebService.getDateEnd()));
            pLoanDatabase.setDateReturn(vDateConverter.toDate(pLoanWebService.getDateReturn()));
        } catch (com.antazri.exception.ConvertException pE) {
            logger.error("error.message.mapper.convert");
            throw new ConvertException(pE.getMessage(), setConvertExceptionFault(pE));
        }

        return pLoanDatabase;
    }
}
