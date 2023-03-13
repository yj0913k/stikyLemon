package com.green.nowon.domain.dto.goods;

import com.green.nowon.domain.entity.CategoryGoodsEntity;
import com.green.nowon.domain.entity.GoodsEntity;
import com.green.nowon.domain.entity.ReviewEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GoodsDetailDTO {

    private long no;
    private String title;       //상품명
    private String content;
    private String specifications;  //상품상세
    private String productCare;    //주의사항
    private int price;
    private int stock;


    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String defImgUrl;

    public GoodsDetailDTO(GoodsEntity e){
        this.no=e.getNo();
        this.title=e.getTitle();
        this.content=e.getContent();
        this.specifications=e.getSpecifications();
        this.productCare=e.getProductCare();
        this.price=e.getPrice();
        this.stock=e.getStock();
        this.createdDate=e.getCreatedDate();
        this.updatedDate=e.getUpdatedDate();
        this.defImgUrl=e.defImg().getUrl()+e.defImg().getNewName();
    }
    public GoodsDetailDTO(CategoryGoodsEntity cie){
        this(cie.getGoods());
    }

}
