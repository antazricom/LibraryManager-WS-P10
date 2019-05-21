package com.antazri.model.utils;

import java.util.ResourceBundle;

/**
 * La classe ErrorMessage est utilisé pour charger et permettre l'accès à une liste de messages d'erreur
 * centralisée dans le fichier ErrorMessages.properties. L'objet ResourceBundle permet de gérer
 * l'internationalisation des messages en cas d'ajout de langues dans l'application
 */
public class ErrorMessage {

    protected static final ResourceBundle ERROR_MESSAGES =
            ResourceBundle.getBundle("/com/antazri/ErrorMessages");

    public static ResourceBundle getErrorMessages() {
        return ERROR_MESSAGES;
    }

}
