package com.green.nowon.domain.dto.goods;

import lombok.Data;

@Data
public class ReviewDTO {
    private String text;
    private long rate;
    private long goodsNo;

}
