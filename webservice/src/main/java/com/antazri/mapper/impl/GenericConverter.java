package com.antazri.mapper.impl;

import com.antazri.exception.ConvertException;
import com.antazri.mapper.contract.IGenericConverter;
import com.antazri.model.bean.Fault;
import com.antazri.model.utils.ErrorMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;

/**
 * La class GenericConverter est l'implémentation de l'interface fonctionnelle {@link com.antazri.mapper.contract.IGenericConverter}.
 * Elle permet de gérer la conversion générique des objets générés ou instanciés dans tout le web service.
 * @param <O> est l'objet d'origine dont on veut copier les propriétés
 * @param <C> est l'objet auquel sont destinées les copies des propriétés
 */
public class GenericConverter<O, C> implements IGenericConverter<O, C> {

    private static final Logger logger = LogManager.getLogger(GenericConverter.class);

    /**
     * La méthode implémentée utilise la classe BeanUtils fournie par Spring Framework. Elle dispose d'une méthode
     * copyProperties afin de faciliter l'affectation des attributs de la classe d'origine O à la classe convertie C.
     * @param pOriginalObject est un objet d'une classe O dont on veut copier les propriétés
     * @param pConvertedObject est un objet d'une classe C auquel on veut appliquer les propriétés
     * @return un objet convertie C instancié et disposant des propriétés de l'objet d'origine O
     * @throws ConvertException
     * @see BeanUtils
     */
    @Override
    public C toConvert(O pOriginalObject, C pConvertedObject) throws ConvertException {
        try {
            BeanUtils.copyProperties(pOriginalObject, pConvertedObject);
        } catch (Exception pE) {
            logger.error(ErrorMessage.getErrorMessages().getString("error.message.mapper.convert") + " (toConvert Mapper)");
            throw new ConvertException(ErrorMessage.getErrorMessages().getString("error.message.mapper.convert"),
                    new Fault("BeanUtils copyProperties",
                            ErrorMessage.getErrorMessages().getString("error.message.mapper.convert") + " : " + this.getClass().getName()));
        }

        return pConvertedObject;
    }
}
