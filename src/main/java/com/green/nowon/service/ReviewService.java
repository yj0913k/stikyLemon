package com.green.nowon.service;

import com.green.nowon.domain.dto.goods.ReviewDTO;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


public interface ReviewService {


    void insertReview(ReviewDTO dto, long mno);
}
