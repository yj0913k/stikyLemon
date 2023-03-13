package com.green.nowon.controller;

import com.green.nowon.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService service;

    @GetMapping("/comm/categorys")
    public String fistCategory(Model model) {
        service.fistCategory(model);
        return "adminpage/category/category";
    }

    @GetMapping("/comm/categorys/{parentNo}")
    public String categoryList(@PathVariable long parentNo, Model model) {
        service.categoryList(parentNo, model);
        return "adminpage/category/category";
    }
   
}
