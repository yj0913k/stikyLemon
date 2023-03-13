package com.green.nowon.domain.dto.goods;

import com.green.nowon.domain.entity.GoodsEntity;
import com.green.nowon.domain.entity.GoodsImg;
import com.green.nowon.utils.MyFileUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GoodsInsertDTO {

    private long[]  categoryNo;
    private String title;       //상품명
    private String specifications;  //상품상세
    private String productCare;    //주의사항
    private String content;
    private int price;
    private int stock;


    private String[] newName;
    private String[] orgName;

    public List<GoodsImg> toItemListImgs(GoodsEntity goods, String url) {
        List<GoodsImg> imgs=new ArrayList<>();
        for(int i=0; i<orgName.length; i++) {
            if(orgName[i].equals("") || orgName[i]==null)continue;
            boolean defImg=false;
            if(i==0)defImg=true;
            GoodsImg ent=GoodsImg.builder()
                    .url(url)
                    .orgName(orgName[i])
                    .newName(newName[i])
                    .defImg(defImg)
                    .goods(goods)//fk설정을 위한
                    .build();
            imgs.add(ent);
        }

        //temp 폴더 상위폴더인 upload로 이동
        MyFileUtils.moveUploadLocationFromTemp(newName,url);

        return imgs;
    }

    public GoodsEntity toGoodsEntity() {
        return GoodsEntity.builder()
                .title(title).content(content).price(price).stock(stock)
                .specifications(specifications).productCare(productCare)
                .build();
    }
}
