package com.antazri.bo.utils.impl;

import com.antazri.bo.utils.contract.IDateExtension;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;
import java.util.Date;

/**
 * La classe DataExtension implémente l'interface fonctionnelle IDateExtension. Elle permet de mettre en place la
 * mise à jour des attributs DateEnd d'un objet Loan selon les paramètres définis dans le fichier config.properties
 * chargé depuis l'instance de BOConfig
 */
public class DateExtension implements IDateExtension {

    private final Logger logger = LogManager.getLogger(DateExtension.class);
    private BOConfig boConfig = new BOConfig();
    private String TIME_PROPERTY = "loan.duration.days";

    /**
     * La méthode extendEndDate permet de modifier la date de l'attribut DateEnd passé en paramètre.
     * @param pOriginalDate est un objet java.util.Date issu de du Business Object LoanBo
     * @return un objet java.util.Date mis à jour
     */
    @Override
    public Date extendEndDate(Date pOriginalDate, int pExtension) {
        Calendar vCalendar = Calendar.getInstance();
        vCalendar.setTime(pOriginalDate);

        if (TIME_PROPERTY.equals("loan.duration.weeks")) {
            vCalendar.add(Calendar.WEEK_OF_YEAR, pExtension);
        } else {
            vCalendar.add(Calendar.DAY_OF_YEAR, pExtension);
        }

        return vCalendar.getTime();
    }

    /**
     * Retourne un Integer de la configuration définissant le nombre de jours / semaines maximum
     * autorisé pour étendre un prêt
     * @return un Integer (du fichier de configuration config.properties)
     */
    public int getMaximumExtension() {
        if (TIME_PROPERTY.equals("loan.duration.weeks")){
            return Integer.parseInt(boConfig.getPropertiesFile().getProperty("loan.duration.weeks"));
        }

        return Integer.parseInt(boConfig.getPropertiesFile().getProperty("loan.duration.days"));

    }
}
