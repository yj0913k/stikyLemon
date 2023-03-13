package com.green.nowon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.green.nowon.service.GoodsService;
@Controller
public class GoodsController {
	
	@Autowired
	private GoodsService service;
	
	@GetMapping("/common/goods/{no}")
	public String detail(@PathVariable long no, Model model) {
		//service.detail(no, model);
		return "views/goods/goodsDetailPage";
	}
	
	 @GetMapping("/comm/category/{no}/goods")
		public String goodsOfCategory(@PathVariable long no, Model model) {
			service.goodsOfCategory(no, model);
			return "views/main/menu/shop";
	}
}
