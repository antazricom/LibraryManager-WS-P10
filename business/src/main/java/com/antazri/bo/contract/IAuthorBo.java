package com.antazri.bo.contract;

import com.antazri.model.bean.Author;

import java.util.List;

public interface IAuthorBo {

    Author findById(int pId);
    List<Author> findAll();
    Author add(Author pAuthor);
    Author update(Author pAuthor);
    void delete(Author pAuthor);
}
