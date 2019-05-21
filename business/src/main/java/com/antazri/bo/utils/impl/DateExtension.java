package com.antazri.bo.utils.impl;

import com.antazri.bo.utils.contract.IDateExtension;

import java.util.Calendar;
import java.util.Date;

/**
 * La classe DataExtension implémente l'interface fonctionnelle IDateExtension. Elle permet de mettre en place la
 * mise à jour des attributs DateEnd d'un objet Loan selon les paramètres définis dans le fichier config.properties
 * chargé depuis l'instance de BOConfig
 */
public class DateExtension implements IDateExtension {

    private BOConfig boConfig = new BOConfig();
    private String TIME_PROPERTY = "loan.duration.weeks";

    /**
     * La méthode extendEndDate permet de modifier la date de l'attribut DateEnd passé en paramètre.
     * @param pOriginalDate est un objet java.util.Date issu de du Business Object LoanBo
     * @return un objet java.util.Date mis à jour
     */
    @Override
    public Date extendEndDate(Date pOriginalDate) {
        Calendar vCalendar = Calendar.getInstance();
        vCalendar.setTime(pOriginalDate);

        if (TIME_PROPERTY.equals("loan.duration.weeks")) {
            vCalendar.add(Calendar.WEEK_OF_YEAR, Integer.parseInt(boConfig.getPropertiesFile().getProperty(TIME_PROPERTY)));
        } else {
            vCalendar.add(Calendar.DAY_OF_YEAR, Integer.parseInt(boConfig.getPropertiesFile().getProperty(TIME_PROPERTY)));
        }

        return vCalendar.getTime();
    }
}
