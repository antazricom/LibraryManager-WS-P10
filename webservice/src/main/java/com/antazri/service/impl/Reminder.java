package com.antazri.service.impl;


import com.antazri.bo.contract.ILoanBo;
import com.antazri.exception.ConvertException;
import com.antazri.generated.batch.*;
import com.antazri.mapper.contract.IDateConverter;
import com.antazri.mapper.contract.IGenericConverter;
import com.antazri.mapper.impl.DateConverter;
import com.antazri.mapper.impl.GenericConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * La classe Reminder est l'implémentation de la classe {@link com.antazri.generated.batch.BatchPort} générée par
 * le fichier reminder.wsdl. Cette classe est le service utilisé par le Batch pour récupérer et gérer les prêts
 */
@Service
public class Reminder implements BatchPort {

    private static final Logger logger = LogManager.getLogger(Reminder.class);

    @Autowired
    private ILoanBo loanBo;

    private IGenericConverter<com.antazri.model.bean.Loan, Loan> vGenericConverterForWebService = new GenericConverter<>();
    private IDateConverter dateConverter = new DateConverter();

    /**
     * La méthode doReminder permet au service de récupérer et retourner l'ensemble des instances de Loan en utilisant
     * la méthode "findLateLoan" de {@link com.antazri.bo.impl.LoanBo}.
     * @param parameters est un objet {@link com.antazri.generated.batch.DoReminderRequest} requis par le webservice
     * @return {@link com.antazri.generated.batch.DoReminderResponse} contenant les propriétés nécessaires à la réponse
     * du web service
     * @throws ReminderException
     */
    @Override
    public DoReminderResponse doReminder(DoReminderRequest parameters) throws ReminderException {
        DoReminderResponse vDoReminderResponse = new DoReminderResponse();

        List<com.antazri.model.bean.Loan> vLoanDatabase = loanBo.findLateLoan();

        try {
            for (com.antazri.model.bean.Loan loan : vLoanDatabase) {
                vDoReminderResponse.getLoans().add(convertToWebService(loan, vGenericConverterForWebService.toConvert(loan, new Loan())));
            }
        } catch (ConvertException pE) {
            logger.error("doReminder: Erreur dans la conversion des Loan depuis la base de données");
            throw new ReminderException("doReminder: Erreur dans la conversion des Loan depuis la base de données");
        }

        return vDoReminderResponse;
    }

    private Loan convertToWebService(com.antazri.model.bean.Loan pDatabaseLoan, Loan pWebServiceLoan) throws ConvertException {
        GenericConverter<com.antazri.model.bean.Member, Member> vMemberGenericConverter = new GenericConverter<>();
        GenericConverter<com.antazri.model.bean.Book, Book> vBookGenericConverter = new GenericConverter<>();

        try {
            pWebServiceLoan.setDateBegin(dateConverter.toXMLGregorianCalendar(pDatabaseLoan.getDateBegin()));
            pWebServiceLoan.setDateEnd(dateConverter.toXMLGregorianCalendar(pDatabaseLoan.getDateBegin()));
            pWebServiceLoan.setDateReturn(dateConverter.toXMLGregorianCalendar(pDatabaseLoan.getDateBegin()));
            pWebServiceLoan.setBook(vBookGenericConverter.toConvert(pDatabaseLoan.getBook(), new Book()));
            pWebServiceLoan.setMember(vMemberGenericConverter.toConvert(pDatabaseLoan.getMember(), new Member()));
        } catch (ConvertException pE) {
            logger.error("doReminder: Erreur dans la conversion des Loan depuis la base de données");
            com.antazri.model.bean.Fault fault = new com.antazri.model.bean.Fault();
            fault.setFaultCode("500");
            fault.setFaultString("convertToWebService: Erreur dans la conversion des Loan depuis la base de données");
            throw new ConvertException("convertToWebService: Erreur dans la conversion des Loan depuis la base de données", fault);
        }



        return pWebServiceLoan;
    }
}
