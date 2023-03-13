package com.green.nowon.security;

import com.green.nowon.domain.entity.MemberEntity;
import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.asm.Advice;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class MyUserDetails extends User  implements OAuth2User {

    private long mno;
    private String email;
    private String name;
    private String nickName;
    private boolean social;
    private boolean deleted;

    private Map<String, Object> attributes;

    public MyUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public MyUserDetails(MemberEntity entity){
        this(entity.getEmail(), entity.getPass(),
                entity.getRoles()
                .stream()
                .map(myRole -> new SimpleGrantedAuthority(myRole.getRole()))// "ROLE_USER" or "ROLE_ADMIN"
                .collect(Collectors.toSet()));
        this.email=entity.getEmail();
        this.name=entity.getName();
        this.nickName = entity.getNickName();    //null일 수 있으므로
        this.social=entity.isSocial();
        this.deleted= entity.isDeleted();
        this.mno=entity.getMno();
    }

    //소셜로그인일시 진입점
    public MyUserDetails(String email, String pass, Set<SimpleGrantedAuthority> authorities, String nickName) {
        this(email, pass, authorities);
        this.email=email;
        this.social=true;
        this.name=nickName;
        this.nickName=nickName;
        this.deleted=false;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }
}
