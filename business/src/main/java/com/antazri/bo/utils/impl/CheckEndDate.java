package com.antazri.bo.utils.impl;

import com.antazri.bo.utils.contract.ICheckDateEnd;

import java.time.Instant;
import java.util.Date;

/**
 * La classe CheckEndDate permet de vérifier la propriété DateEnd d'un objet
 */
public class CheckEndDate implements ICheckDateEnd {

    /**
     * La méthode isDateEndPassed vérifie si l'attribut DateEnd est une date passée par rapport à l'instant
     * pendant lequel la méthode est appelée
     * @param pDateEnd est un objet java.util.Date
     * @return un booléen à TRUE si la date est passée ou FALSE si ce n'est pas passé
     */
    @Override
    public boolean isDateEndPassed(Date pDateEnd) {

        if (pDateEnd.before(Date.from(Instant.now()))) {
            return true;
        }

        return false;
    }
}
