package com.green.nowon.domain.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GoodsImgRepository extends JpaRepository<GoodsImg,Long> {
    List<GoodsImg> findAllByGoodsNo(long no);

    Optional<GoodsImg> findByDefImgAndGoodsNo(boolean b, long no);

    void deleteByNewName(String newName);

    void deleteByGoodsNo(long no);
}
