package com.antazri.security.contract;

import com.antazri.model.bean.Token;

public interface IGenerateToken {

    Token generate(String pLogin);
}
