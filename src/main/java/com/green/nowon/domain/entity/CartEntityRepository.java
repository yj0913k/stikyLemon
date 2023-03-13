package com.green.nowon.domain.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartEntityRepository  extends JpaRepository<CartEntity,Long> {
    Optional<CartEntity> findByMember_mno(long mno);
}
