package com.green.nowon.domain.entity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderGoodsEntityRepository extends JpaRepository<OrderGoodsEntity, Long>{

	List<OrderGoodsEntity> findByOrder(OrderEntity e);

	

}
