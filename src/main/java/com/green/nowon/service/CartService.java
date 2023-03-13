package com.green.nowon.service;

import org.springframework.ui.Model;

public interface CartService {
    void createCart(long mno);

    void getCartList(long mno, Model model);

    void insertData(long goodsNo, long quantity, long mno);

    void updateCart(long no, long quantity);

    void deleteCartGoods(long no);
}
