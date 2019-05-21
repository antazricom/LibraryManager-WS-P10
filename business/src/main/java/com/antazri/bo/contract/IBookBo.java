package com.antazri.bo.contract;

import com.antazri.model.bean.Author;
import com.antazri.model.bean.Book;
import com.antazri.model.bean.Category;

import java.util.List;

public interface IBookBo {

    Book findById(int pId);
    List<Book> findByTitle(String pTitle);
    List<Book> findByAuthor(Author pAuthor);
    List<Book> findByCategory(Category pCategory);
    List<Book> findAll();
    Book add(Book pBook);
    Book update(Book pBook);
    void delete(Book pBook);
}
