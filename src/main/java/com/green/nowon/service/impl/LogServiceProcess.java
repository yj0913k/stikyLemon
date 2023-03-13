package com.green.nowon.service.impl;

import com.green.nowon.domain.dto.member.MemberDTO;
import com.green.nowon.domain.dto.member.MemberInsertDTO;
import com.green.nowon.domain.entity.MemberEntity;
import com.green.nowon.domain.entity.MemberEntityRepository;
import com.green.nowon.security.MyRole;
import com.green.nowon.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LogServiceProcess implements LogService {

    //DAO :: JPA Repository
    @Autowired
    private MemberEntityRepository repository;

    @Autowired
    private PasswordEncoder pe;
    //회원가입처리
    @Override
    public void save(MemberInsertDTO dto) {
        //비밀번호 인코딩::Security에서는 비밀번호는 인코딩된 것만 허용
        //role 세팅
        repository.save(dto.toEntity(pe)
                .addRole(MyRole.USER));
    }
}
