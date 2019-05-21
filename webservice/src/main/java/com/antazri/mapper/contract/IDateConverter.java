package com.antazri.mapper.contract;

import com.antazri.exception.ConvertException;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;

public interface IDateConverter {

    XMLGregorianCalendar toXMLGregorianCalendar(Date pDate) throws ConvertException;
    Date toDate(XMLGregorianCalendar pXmlGregorianCalendar) throws ConvertException;

}
