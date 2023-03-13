package com.green.nowon.domain.dto.goods;

import com.green.nowon.domain.entity.GoodsEntity;
import com.green.nowon.domain.entity.MemberEntity;
import com.green.nowon.domain.entity.ReviewEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.persistence.*;

@Getter
public class ReviewListDTO {

    private  String memberName;
    private long rno;
    private String text;
    private long rate;
    private long mno;
    private long gno;

    public ReviewListDTO(ReviewEntity e) {
        this.rno = e.getRno();
        this.text = e.getText();
        this.rate = e.getRate();
        this.mno = e.getMember().getMno();
        this.memberName= e.getMember().getName();
        this.gno = e.getGoods().getNo();
    }
}
