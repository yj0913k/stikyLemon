package com.green.nowon.domain.entity;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Repository
public interface ReviewEntityRepository extends JpaRepository<ReviewEntity, Long> {

    List<ReviewEntity> findAllByGoodsNoOrderByCreatedDateDesc(long no);

    List<ReviewEntity> findByGoodsNo(long no);
}