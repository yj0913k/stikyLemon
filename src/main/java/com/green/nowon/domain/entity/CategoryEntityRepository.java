package com.green.nowon.domain.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryEntityRepository extends JpaRepository<CategoryEntity,Long> {
    Optional<CategoryEntity> findByParentNoAndName(Long parentNo, String name);

    List<CategoryEntity> findByParentNoOrderByNameAsc(Object o);
}
