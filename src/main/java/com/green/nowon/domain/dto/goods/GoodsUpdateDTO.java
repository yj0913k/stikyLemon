package com.green.nowon.domain.dto.goods;

import com.green.nowon.domain.entity.CategoryGoodsEntity;
import com.green.nowon.domain.entity.GoodsEntity;
import com.green.nowon.domain.entity.GoodsImg;
import com.green.nowon.utils.MyFileUtils;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class GoodsUpdateDTO {

    private long no;
    private String title;       //상품명
    private String content;
    private String specifications;  //상품상세
    private String productCare;    //주의사항
    private int price;
    private int stock;

    private List<String> orgName;
    private List<String> newName;
    private List<String> baseImgList;

    public GoodsEntity toGoodsEntity() {
        return GoodsEntity.builder()
                .no(no)
                .title(title).content(content).price(price).stock(stock)
                .specifications(specifications).productCare(productCare)
                .build();
    }

    public Map<String,List<String>> toGoodsImg(GoodsEntity goods, String url) {
        Map<String,List<String>> addDeleteInfoMap = new HashMap<>();
        newName.remove("");
        orgName.remove("");
        //대표이미지 처리 먼저
        if(!baseImgList.get(0).equals(newName.get(0))){
            //대표이미지 변경되었으면
            List<String> changeDef = new ArrayList<>();
            changeDef.add( newName.get(0));
            changeDef.add( orgName.get(0));
            addDeleteInfoMap.put("defImg",changeDef);
            MyFileUtils.moveUploadLocationFromTemp(changeDef.toArray(new String[2]),url);
            baseImgList.remove(0);
            newName.remove(0);
        }
        //삭제처리된 것. -->DB에서 삭제
        List<String> deletedImg  = baseImgList.stream()
                .filter(i -> !newName.contains(i))
                .collect(Collectors.toList());


        //새로 생긴것.-->DB에 추가
        List<String> addImg  = newName.stream()
                .filter(i -> !baseImgList.contains(i))
                .collect(Collectors.toList());

        addDeleteInfoMap.put("delete", deletedImg);
        addDeleteInfoMap.put("add", addImg);

        int arrListSize = addImg.size();
        String[] arr = addImg.toArray(new String[arrListSize]);
        //추가된거는 temp 폴더 상위폴더인 upload로 이동
        MyFileUtils.moveUploadLocationFromTemp(arr,url);
        return addDeleteInfoMap;
    }
}
