package com.green.nowon.service;

import org.springframework.ui.Model;

public interface CategoryService {
    void save(String[] name);

    void fistCategory(Model model);

    void categoryList(Long parentNo, Model model);



}
