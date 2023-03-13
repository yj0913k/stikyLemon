package com.green.nowon.domain.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartGoodsDetailRepository extends JpaRepository<CartGoodsDetail,Long> {
    List<CartGoodsDetail> findByGoodsNo(long mno);

    List<CartGoodsDetail> findByCart(CartEntity cart);

    Optional<CartGoodsDetail> findByGoodsNoAndCartMemberMno(long goodsNo, long mno);
}
