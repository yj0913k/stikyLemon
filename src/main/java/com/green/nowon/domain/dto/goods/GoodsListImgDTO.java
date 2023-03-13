package com.green.nowon.domain.dto.goods;

import com.green.nowon.domain.entity.GoodsImg;

import lombok.Getter;

@Getter
public class GoodsListImgDTO {
	private long no;
	private String orgName;
	private String newName;
	private String url;
	private boolean defImg;
	
	//편의필드
	private String imgUrl;
	
	public GoodsListImgDTO(GoodsImg e) {
		this.no = e.getNo();
		this.orgName = e.getNewName();
		this.newName = e.getNewName();
		this.url = e.getUrl();
		this.defImg = e.isDefImg();
		
		this.imgUrl=url+newName;
	}
	
	
}