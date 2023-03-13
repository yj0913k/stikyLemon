package com.green.nowon.service;

import org.springframework.security.core.Authentication;

import com.green.nowon.domain.dto.board.BoardUpdateDTO;
import com.green.nowon.domain.dto.member.MemberUpdateDTO;

public interface MemberService {

	void updateMember(MemberUpdateDTO dto, Authentication auth);

}
