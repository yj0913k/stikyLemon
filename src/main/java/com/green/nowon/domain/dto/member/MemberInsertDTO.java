package com.green.nowon.domain.dto.member;


import com.green.nowon.domain.entity.MemberEntity;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Setter
public class MemberInsertDTO {

    private String email;
    private String pass;
    private String name;

    //최종적으로 DB에 저장될때는 DTO의 필드가 MemberEntity클래스에 매핑
    //DTO->Entity 편의메서드 이용
    public MemberEntity toEntity(PasswordEncoder pe){
        return MemberEntity.builder()
                .email(email).pass(pe.encode(pass)).name(name)
                .build();
    }
}
