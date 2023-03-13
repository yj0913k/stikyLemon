package com.green.nowon;

import com.green.nowon.domain.entity.*;
import com.green.nowon.security.MyRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.green.nowon.domain.entity.MemberEntity;
import com.green.nowon.domain.entity.MemberEntityRepository;
import com.green.nowon.security.MyRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;

@SpringBootTest
class SpringWebSampleApplicationTests {


    //멤버엔티티에 접근할 멤레포
    @Autowired
    MemberEntityRepository mrepo;
    //패스워드인코딩
    @Autowired
    PasswordEncoder pe;

    @Autowired
    private DeliveryEntityRepository deliveryRepo;


    @Test
    void 배송지정보등록() {
       /* deliveryRepo.save(dto.toEntity()
                .base(deliveryRepo.countByMember_email(email)==0?true:false)//배송지정보가 없으면 base=true
                .member(memRepo.findByEmail(email).orElseThrow()))*/
        deliveryRepo.save(DeliveryEntity.builder()
                .base(true)
                .detailAddress("상세주소")
                        .receiverName("수령자이름")
                        .phone1("폰번호1")
                        .phone2("폰번호2")
                        .postcode("우편번호")
                        .deliveryAddrName("배송지이름")
                        .roadAddress("도로명주소")
                        .jibunAddress("지번주소")
                        .detailAddress("상세주소")
                        .extraAddress("추가주소")
                        .member(mrepo.findById(1L).get())
                .build());
    }

    //@Test
    void 회원가입이잘되는지() {
        mrepo.save(MemberEntity.builder()
                .name("관리자")
                .email("admin@test.com")
                .pass(pe.encode("1234"))
                .build()
                .addRole(MyRole.USER)
                .addRole(MyRole.ADMIN)
        );
    }
}

