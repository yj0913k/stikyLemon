package com.green.nowon.domain.entity;

import com.green.nowon.domain.dto.member.MemberUpdateDTO;
import com.green.nowon.security.MyRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@DynamicUpdate
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "member")
@Entity
public class MemberEntity extends BaseDateEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long mno;   //회원번호

    @Column(unique = true,nullable = false)   //unique not null
    private String email;   //username

    @Column(nullable = false)   //not null
    private String pass;    //password

    @Column(nullable = false)   //not null
    private String name;

    @Column(unique = true)
    private String nickName;

    private boolean social;     // 소셜유저여부
    private boolean deleted ;    // 탈퇴여부

    //ROLE정보 --enum 사용
    @Builder.Default
    @CollectionTable(name = "role")
    @Enumerated(EnumType.STRING)    //저장유형 문자열로(롤 확장시 유리) 기본 ordinal(숫자)
    @ElementCollection(fetch = FetchType.EAGER) //1:N member테이블에서만 접근가능한 내장테이블?로 만들어줌
    private Set<MyRole> roles = new HashSet<>();
    //role적용을 위한 편의메서드
    public MemberEntity addRole(MyRole role){
        this.roles.add(role);
        return this;
    }

    //배송지정보
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    private List<DeliveryEntity> delivery = new ArrayList<>();

    public MemberEntity addAddress(DeliveryEntity dest){
        this.delivery.add(dest);
        return this;
    }

	public MemberEntity update(MemberUpdateDTO dto ,PasswordEncoder pe) {
		if(dto.getEmail()!=null && !dto.getEmail().trim().equals(""))email=dto.getEmail();
		if(dto.getName()!=null && !dto.getName().trim().equals(""))name=dto.getName();
		if(dto.getPass()!=null && !dto.getPass().trim().equals(""))pass= pe.encode(dto.getPass());
		if(dto.getNickName()!=null && !dto.getNickName().trim().equals(""))nickName=dto.getNickName();
		return this;
	}

}