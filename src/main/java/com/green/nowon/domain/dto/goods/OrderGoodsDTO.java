package com.green.nowon.domain.dto.goods;

import com.green.nowon.domain.entity.CartGoodsDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderGoodsDTO {
    private long goodsNo;
    private long quantity;

    public OrderGoodsDTO(CartGoodsDetail e){
        this.goodsNo=e.getGoods().getNo();
        this.quantity=e.getQuantity();
    }
}

