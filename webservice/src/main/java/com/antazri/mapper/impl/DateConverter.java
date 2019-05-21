package com.antazri.mapper.impl;

import com.antazri.exception.ConvertException;
import com.antazri.mapper.contract.IDateConverter;
import com.antazri.model.bean.Fault;
import com.antazri.model.utils.ErrorMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * La classe DateConverter est l'implémentation de l'interface fonctionnelle {@link com.antazri.mapper.contract.IDateConverter}
 * et utilisée afin de convertir les formats de Date utilisés par la base de données et le web service pour garantir
 * la transaction des données de l'un à l'autre
 */
public class DateConverter implements IDateConverter {

    private static final Logger logger = LogManager.getLogger(DateConverter.class);

    /**
     * La méthode toXMLGregorianCalendar est utilisée pour convertir un objet java.util.Date issu de la base de données
     * via la DAO en un format XMLGregorianCalendar utilisé par le webservice.
     * @param pDate est un objet (java.util.)Date utilisé par la base de données
     * @return un objet XMLGregorianCalendar
     * @throws ConvertException
     * @see com.antazri.service.impl.LoanManagement
     * @see com.antazri.service.impl.BookManagement
     */
    public XMLGregorianCalendar toXMLGregorianCalendar(Date pDate) throws ConvertException {
        GregorianCalendar vGregorianCalendar = new GregorianCalendar();
        vGregorianCalendar.setTime(pDate);
        XMLGregorianCalendar vXmlGregorianCalendar = null;

        try {
            vXmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(vGregorianCalendar);
        } catch (DatatypeConfigurationException pE) {
            throw new ConvertException(ErrorMessage.getErrorMessages().getString("error.message.mapper.convert"),
                    new Fault("Erreur Date to XML convert",ErrorMessage.getErrorMessages().getString("error.message.mapper.convert")));
        }

        return vXmlGregorianCalendar;
    }

    /**
     * La méthode toDate est utilisée pour convertir un objet XMLGregorianCalendar issu du web service
     * via les services en un format java.util.Date utilisé par la base de données.
     * @param pXmlGregorianCalendar est un objet XMLGregorianCalendar utilisé par le web service
     * @return un objet java.util.Date
     * @throws ConvertException
     * @see com.antazri.service.impl.LoanManagement
     * @see com.antazri.service.impl.BookManagement
     */
    public Date toDate(XMLGregorianCalendar pXmlGregorianCalendar) throws ConvertException {

        if(pXmlGregorianCalendar == null) {
            throw new ConvertException(ErrorMessage.getErrorMessages().getString("error.message.mapper.convert"),
                    new Fault("Erreur XML to Date convert",ErrorMessage.getErrorMessages().getString("error.message.mapper.convert")));
        }

        return pXmlGregorianCalendar.toGregorianCalendar().getTime();
    }
}
