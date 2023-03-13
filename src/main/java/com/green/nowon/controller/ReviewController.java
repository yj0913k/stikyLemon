package com.green.nowon.controller;

import com.green.nowon.domain.dto.goods.ReviewDTO;
import com.green.nowon.security.MyUserDetails;
import com.green.nowon.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @PostMapping("/comm/goods/detail/review")
    public String review(ReviewDTO dto, @AuthenticationPrincipal MyUserDetails userDetails) {
        System.out.println(">>>>>"+dto);
         reviewService.insertReview(dto, userDetails.getMno());
         return "redirect:/comm/goods/detail/"+dto.getGoodsNo();
    }

}
