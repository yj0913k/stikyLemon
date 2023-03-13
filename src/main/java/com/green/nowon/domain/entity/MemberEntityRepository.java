package com.green.nowon.domain.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberEntityRepository extends JpaRepository<MemberEntity,Long> {

    //쿼리 메서드 유형 : 문법에 맞지않으면 오류발생
    Optional<MemberEntity> findByEmailAndSocialAndDeleted(String username, boolean b, boolean b1);

    Optional<MemberEntity> findByEmailAndDeleted(String email, boolean b);

    Optional<MemberEntity> findByEmail(String email);
}
