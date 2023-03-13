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
@Entity
public class GoodsImg extends BaseDateEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long no;
    private String orgName;
    private String newName;
    private String url;
    private boolean defImg;

    @JoinColumn//goods_no
    @ManyToOne
    private GoodsEntity goods;

    public GoodsImg update(String newName, String orgName) {
        this.newName=newName;
        this.orgName=orgName;
        return this;
    }
}


