package com.antazri.bo.contract;

import com.antazri.model.bean.Category;

import java.util.List;

public interface ICategoryBo {

    Category findById(int pId);
    List<Category> findAll();
    Category add(Category pCategory);
    Category update(Category pCategory);
    void delete(Category pCategory);
}
