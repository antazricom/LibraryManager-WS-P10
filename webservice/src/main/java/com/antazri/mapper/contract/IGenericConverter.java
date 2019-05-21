package com.antazri.mapper.contract;

import com.antazri.exception.ConvertException;

public interface IGenericConverter<O, C> {

    C toConvert(O pOriginalObject, C pConvertedObject) throws ConvertException;
}
