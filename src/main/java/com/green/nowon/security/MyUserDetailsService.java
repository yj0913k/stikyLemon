package com.green.nowon.security;

import com.green.nowon.domain.entity.MemberEntity;
import com.green.nowon.domain.entity.MemberEntityRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Log4j2
public class MyUserDetailsService implements UserDetailsService {

    //DB의 테이블에서 인증처리하기 위한 메서드

    @Autowired
    private MemberEntityRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(">>>>>login page: email -> username ::::: "+username);
        MemberEntity entity =  repo.findByEmailAndSocialAndDeleted(username,false,false)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 이메일"));   //

        //email, pass, roles(Collection<? extends org.springframework.security.core.GrantedAuthority> authorities)
        // enum MyRole -> SimpleGrantedAuthority
/*

        Set<SimpleGrantedAuthority> authorities =
                entity.getRoles()
                .stream()
                .map(myRole -> new SimpleGrantedAuthority(myRole.getRole()))// "ROLE_USER" or "ROLE_ADMIN"
                .collect(Collectors.toSet());

        return new MyUserDetails(username, entity.getPass(),authorities);
*/
        return new MyUserDetails(entity);

    }
}
