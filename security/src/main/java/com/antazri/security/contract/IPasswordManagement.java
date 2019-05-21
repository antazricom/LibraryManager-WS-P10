package com.antazri.security.contract;

import com.antazri.model.bean.Member;
import com.antazri.security.exception.BadLoginException;

public interface IPasswordManagement {

    boolean isValidPassword(Member pMember, String pPlainPassword) throws BadLoginException;
    String hashPassword(String pPassword);
}
