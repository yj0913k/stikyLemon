package com.green.nowon.domain.dto.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
public class OrderInsertDTO {

	private List<OrderGoodsInsertDTO> orderGoods;
	private String paymentNo;
	private long deliveryNo;
}
