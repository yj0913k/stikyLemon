package com.green.nowon.service.impl;

import java.security.Principal;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.green.nowon.domain.dto.board.BoardUpdateDTO;
import com.green.nowon.domain.dto.member.MemberDTO;
import com.green.nowon.domain.dto.member.MemberUpdateDTO;
import com.green.nowon.domain.entity.MemberEntity;
import com.green.nowon.domain.entity.MemberEntityRepository;
import com.green.nowon.security.MyUserDetails;
import com.green.nowon.security.MyUserDetailsService;
import com.green.nowon.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {

	
	@Autowired
	MemberEntityRepository memberEntityRepository;
	@Autowired
	PasswordEncoder pe;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	MyUserDetailsService myUserDetailsService;
	
	@Transactional
	@Override
	public void updateMember(MemberUpdateDTO dto, Authentication auth) {
		MemberEntity member=memberEntityRepository.findById(dto.getMno())//원본
		.map(e->e.update(dto, pe)).orElseThrow();//수정처리 @Transactional session유지된상태에서 entity정보가 바뀌면 update처리됨
		
		//System.out.println(authenticationManager);
		
		//authenticationManager.authenticate(auth);
		//System.out.println((MyUserDetails)auth.getPrincipal());
		//auth.getAuthorities().forEach(r->System.out.println(r));
		//세션 등록
       // Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), auth.getAuthorities()));
       //SecurityContextHolder.getContext().setAuthentication(authentication);
		
	}

}
