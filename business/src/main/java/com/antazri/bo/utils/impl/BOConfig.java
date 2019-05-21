package com.antazri.bo.utils.impl;

import com.antazri.bo.utils.contract.IBOConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * L'objet BOConfig est utilisé pour chargé un fichier de configuration config.properties permettant de centraliser
 * les paramètres utilisés dans la bouche Business
 */
public class BOConfig implements IBOConfig {

    private static final String PROPERTIES_FILE = "config.properties";
    private Properties properties;
    private InputStream inputStream;

    /**
     * La méthode getPropertiesFile utilise des objets Properties et InputStream pour récupérer, lire et affecter
     * le fichier défini par l'attribut PROPERTIES_FILE
     * @return un objet Properties auquel le fichier est affecté
     */
    @Override
    public Properties getPropertiesFile() {
        properties = new Properties();

        inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE);

        try {
            properties.load(inputStream);
        } catch (IOException pE) {
            pE.printStackTrace();
        }

        return this.properties;
    }
}
