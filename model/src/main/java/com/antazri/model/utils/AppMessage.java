package com.antazri.model.utils;

import java.util.ResourceBundle;

/**
 * La classe AppMessage est utilisé pour charger et permettre l'accès à une liste de messages généraux
 * centralisée dans le fichier AppMessages.properties. L'objet ResourceBundle permet de gérer
 * l'internationalisation des messages en cas d'ajout de langues dans l'application
 */
public class AppMessage {

    protected static final ResourceBundle APP_MESSAGES =
            ResourceBundle.getBundle("/com/antazri/AppMessages");

    public static ResourceBundle getAppMessages() {
        return APP_MESSAGES;
    }
}
