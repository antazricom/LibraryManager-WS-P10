package com.antazri.dao.contract;

import com.antazri.model.bean.Author;

import java.util.List;

public interface IAuthorDao {

    Author findById(int pId);
    List<Author> findAll();
    Author add(Author pAuthor);
    Author update(Author pAuthor);
    void delete(Author pAuthor);
}
