package com.green.nowon.service.impl;

import com.green.nowon.domain.dto.cart.CartGoodsListDTO;
import com.green.nowon.domain.entity.*;
import com.green.nowon.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CartServiceProcess implements CartService {

    @Value("${file.location.upload}")
    private String locationUpload;
    @Autowired
    CartEntityRepository cartRepo;

    @Autowired
    CartGoodsDetailRepository cartGoodsRepo;

    @Autowired
    GoodsEntityRepository goodsRepo;
    @Autowired
    MemberEntityRepository memberRepo;


    @Override
    public void deleteCartGoods(long no) {
        cartGoodsRepo.deleteById(no);
    }

    @Transactional
    @Override
    public void updateCart(long no, long quantity) {
        System.out.println("CartServiceProcess");
        System.out.println(no);
        System.out.println(quantity);
        cartGoodsRepo.findById(no).orElseThrow().quantity(quantity);
    }

    //장바구니에 담기 클릭시 저장!
    @Transactional
    @Override
    public void insertData(long goodsNo, long quantity, long mno) {
        Optional<CartGoodsDetail> result = cartGoodsRepo.findByGoodsNoAndCartMemberMno(goodsNo,mno);
        if(result.isEmpty()){
            cartGoodsRepo.save(CartGoodsDetail.builder()
                    .quantity(quantity)
                    .cart(cartRepo.findByMember_mno(mno).get())
                    .goods(goodsRepo.findById(goodsNo).get())
                    .build());
        }
        else {
            cartGoodsRepo.save(result.get().addQuantity(quantity));
        }
    }

    //페이지에 뿌릴 해당하는 멤버의 장바구니리스트 담아줘야함.
    @Override
    public void getCartList(long mno, Model model) {

        CartEntity cart = cartRepo.findByMember_mno(mno).get();
        //List<CartGoodsDetail> result = cartGoodsRepo.findByCart(cart);
        List<CartGoodsListDTO> list =cartGoodsRepo.findByCart(cart).stream()
                .map(CartGoodsListDTO::new).collect(Collectors.toList());
        for(CartGoodsListDTO dto : list){
            GoodsEntity goodsEntity =goodsRepo.findById(dto.getGoodsNo()).orElseThrow();
            dto.setTitle(goodsEntity.getTitle());
            dto.setPrice(goodsEntity.getPrice());
            dto.setDefImg(locationUpload+goodsEntity.defImg().getNewName());
        }
        model.addAttribute("list",list);
    }

    //멤버에 대한 카트 없을시 생성
    @Transactional
    @Override
    public void createCart(long mno) {
        Optional<CartEntity> result = cartRepo.findByMember_mno(mno);
        if(result.isEmpty()){//카트가 생성되어있으면
            cartRepo.save(CartEntity.builder()
                    .member(memberRepo.findById(mno).get()).build());
        }
    }


}
