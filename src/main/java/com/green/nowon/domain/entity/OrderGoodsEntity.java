package com.green.nowon.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "order_goods")
@Entity
public class OrderGoodsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long no;
	
	private int orderPrice;//주문금액
	private long quantity;//주문수량
	
	@JoinColumn//order_no
	@ManyToOne
	private OrderEntity order;
	
	@JoinColumn//goods_no
	@ManyToOne
	private GoodsEntity goods;
	
}
