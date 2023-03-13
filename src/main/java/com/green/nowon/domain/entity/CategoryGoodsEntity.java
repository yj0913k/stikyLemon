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
@Table(name = "category_goods")
@Entity
public class CategoryGoodsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long no;

    @JoinColumn//category_no
    @ManyToOne
    private CategoryEntity category;

    @JoinColumn//goods_no
    @ManyToOne
    private GoodsEntity goods;

}