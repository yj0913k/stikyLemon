package com.green.nowon.domain.dto.goods;

import com.green.nowon.domain.entity.GoodsEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderGoodsListDTO {
	private GoodsListDTO goods;

	private long quantity;

	//수량에따른 가격
	private int totPrice;

	public OrderGoodsListDTO quantity(long quantity) {
		this.quantity=quantity;
		this.totPrice= (int) (quantity*(goods.getPrice()-goods.getSPrice()));
		return this;
	}
	//주문금액
	public OrderGoodsListDTO(GoodsEntity e){
		this.goods=new GoodsListDTO(e);
	}
}
