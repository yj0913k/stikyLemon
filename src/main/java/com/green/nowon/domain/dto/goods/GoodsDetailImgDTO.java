package com.green.nowon.domain.dto.goods;

import com.green.nowon.domain.entity.GoodsImg;
import lombok.Getter;

@Getter
public class GoodsDetailImgDTO {
    private long no;
    private String orgName;
    private String newName;
    private String url;
    private boolean defImg;

    private String totUrl;
    private long goodsNo;

    public GoodsDetailImgDTO(GoodsImg e){
        this.no=e.getNo();
        this.orgName=e.getOrgName();
        this.newName=e.getNewName();
        this.url=e.getUrl();
        this.defImg=e.isDefImg();
        this.goodsNo=e.getGoods().getNo();
        this.totUrl=e.getUrl()+e.getNewName();
    }
}
