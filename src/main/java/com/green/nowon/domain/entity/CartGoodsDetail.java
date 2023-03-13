package com.green.nowon.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class CartGoodsDetail {
    //같은상품을 여러개 주문할 수 있으므로 개수표현을 위한 entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long no;

    private long quantity;//수량

    @JoinColumn//fk:cart_no
    @ManyToOne
    private CartEntity cart;

    @JoinColumn//fk:item_no
    @ManyToOne
    private GoodsEntity goods;

    public CartGoodsDetail addQuantity(long q){
        this.quantity+=q;
        return this;
    }

    public CartGoodsDetail quantity(long q){
        this.quantity = q;
        return this;
    }
}
