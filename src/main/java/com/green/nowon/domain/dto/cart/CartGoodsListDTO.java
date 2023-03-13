package com.green.nowon.domain.dto.cart;

import com.green.nowon.domain.entity.CartGoodsDetail;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartGoodsListDTO {

    private long no;
    private long quantity;//수량
    private long goodsNo;
    private String defImg;  //사진
    private String title;
    private int price;

    public CartGoodsListDTO quantity(int quantity) {
        this.quantity=quantity;
        return this;
    }
    public CartGoodsListDTO(CartGoodsDetail e){
        this.no = e.getNo();
        this.quantity = e.getQuantity();
        this.goodsNo = e.getGoods().getNo();
    }
}
