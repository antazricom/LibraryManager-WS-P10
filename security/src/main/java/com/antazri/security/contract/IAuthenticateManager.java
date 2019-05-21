package com.antazri.security.contract;

import com.antazri.model.bean.Member;
import com.antazri.model.bean.Token;
import com.antazri.security.exception.BadLoginException;

public interface IAuthenticateManager {

    Token login(Member pMember, String pPassword) throws BadLoginException;
}
