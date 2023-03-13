package com.green.nowon.service.impl;

import com.green.nowon.domain.dto.goods.GoodsDetailDTO;
import com.green.nowon.domain.dto.goods.GoodsListDTO;
import com.green.nowon.domain.dto.goods.ReviewDTO;
import com.green.nowon.domain.entity.GoodsEntityRepository;
import com.green.nowon.domain.entity.MemberEntityRepository;
import com.green.nowon.domain.entity.ReviewEntity;
import com.green.nowon.domain.entity.ReviewEntityRepository;
import com.green.nowon.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceProcess implements ReviewService {

    @Autowired
    ReviewEntityRepository entityRepo;

    @Autowired
    GoodsEntityRepository goodsRepo;

    @Autowired
    MemberEntityRepository memberRepo;

    @Override
    public void insertReview(ReviewDTO dto, long mno) {
        entityRepo.save(ReviewEntity.builder()
                .goods(goodsRepo.findById(dto.getGoodsNo()).orElseThrow())
                .member(memberRepo.findById(mno).orElseThrow())
                .text(dto.getText())
                .rate(dto.getRate())
                .build()
        );
    }
}
